package io.github.rezmike.foton.ui.screens.profile

import dagger.Provides
import io.github.rezmike.foton.R
import io.github.rezmike.foton.di.scopes.DaggerScope
import io.github.rezmike.foton.ui.abstracts.AbstractScreen
import io.github.rezmike.foton.ui.activities.root.BottomBarItems
import io.github.rezmike.foton.ui.activities.root.RootActivity

class ProfileScreen : AbstractScreen<RootActivity.RootComponent>() {
    override fun createScreenComponent(parentComponent: RootActivity.RootComponent): Any {
        return DaggerProfileScreen_Component.builder()
                .rootComponent(parentComponent)
                .module(Module())
                .build()
    }

    override fun getLayoutResId(): Int = R.layout.screen_profile

    override fun getCurrentBottomItem() = BottomBarItems.PROFILE

    //region ======================== DI ========================

    @dagger.Component(dependencies = arrayOf(RootActivity.RootComponent::class), modules = arrayOf(Module::class))
    @DaggerScope(ProfileScreen::class)
    interface Component {
        fun inject(presenter: ProfilePresenter)

        fun inject(view: ProfileView)
    }

    @dagger.Module
    class Module {
        @Provides
        @DaggerScope(ProfileScreen::class)
        fun providePresenter() = ProfilePresenter()

        @Provides
        @DaggerScope(ProfileScreen::class)
        fun provideSplashModel() = ProfileModel()
    }

    //endregion
}


