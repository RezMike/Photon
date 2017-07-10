package io.github.rezmike.photon.ui.screens.user

import dagger.Provides
import io.github.rezmike.photon.R
import io.github.rezmike.photon.di.scopes.DaggerScope
import io.github.rezmike.photon.ui.abstracts.AbstractScreen
import io.github.rezmike.photon.ui.activities.root.BottomBarItems
import io.github.rezmike.photon.ui.activities.root.RootActivity

class UserScreen(val userId: String,
                 val bottomBarItem: BottomBarItems = BottomBarItems.MAIN) : AbstractScreen<RootActivity.RootComponent>(bottomBarItem) {

    override fun createScreenComponent(parentComponent: RootActivity.RootComponent): Any {
        return DaggerUserScreen_Component.builder()
                .rootComponent(parentComponent)
                .module(Module(userId, bottomBarItem))
                .build()
    }

    override fun getLayoutResId(): Int = R.layout.screen_user

    //region ======================== DI ========================

    @dagger.Component(dependencies = arrayOf(RootActivity.RootComponent::class), modules = arrayOf(Module::class))
    @DaggerScope(UserScreen::class)
    interface Component {
        fun inject(presenter: UserPresenter)

        fun inject(view: UserView)
    }

    @dagger.Module
    class Module(val userId: String, val bottomBarItem: BottomBarItems) {
        @Provides
        @DaggerScope(UserScreen::class)
        fun providePresenter() = UserPresenter(userId, bottomBarItem)

        @Provides
        @DaggerScope(UserScreen::class)
        fun provideModel() = UserModel()
    }

    //endregion
}