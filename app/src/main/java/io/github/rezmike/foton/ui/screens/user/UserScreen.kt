package io.github.rezmike.foton.ui.screens.user

import dagger.Provides
import io.github.rezmike.foton.R
import io.github.rezmike.foton.di.scopes.DaggerScope
import io.github.rezmike.foton.ui.abstracts.AbstractScreen
import io.github.rezmike.foton.ui.activities.root.BottomBarItems
import io.github.rezmike.foton.ui.activities.root.RootActivity

class UserScreen(val userId: String) : AbstractScreen<RootActivity.RootComponent>() {

    override fun createScreenComponent(parentComponent: RootActivity.RootComponent): Any {
        return DaggerUserScreen_Component.builder()
                .rootComponent(parentComponent)
                .module(Module(userId))
                .build()
    }

    override fun getLayoutResId(): Int = R.layout.screen_user

    override fun getCurrentBottomItem() = BottomBarItems.MAIN

    //region ======================== DI ========================

    @dagger.Component(dependencies = arrayOf(RootActivity.RootComponent::class), modules = arrayOf(Module::class))
    @DaggerScope(UserScreen::class)
    interface Component {
        fun inject(presenter: UserPresenter)

        fun inject(view: UserView)
    }

    @dagger.Module
    class Module(val userId: String) {
        @Provides
        @DaggerScope(UserScreen::class)
        fun providePresenter() = UserPresenter(userId)

        @Provides
        @DaggerScope(UserScreen::class)
        fun provideModel() = UserModel()
    }

    //endregion
}