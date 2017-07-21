package io.github.rezmike.photon.ui.screens.profile

import dagger.Provides
import io.github.rezmike.photon.R
import io.github.rezmike.photon.di.scopes.DaggerScope
import io.github.rezmike.photon.ui.activities.root.BottomBarItems
import io.github.rezmike.photon.ui.activities.root.RootActivity
import io.github.rezmike.photon.ui.screens.AbstractScreen

class ProfileScreen : AbstractScreen<RootActivity.RootComponent>(BottomBarItems.PROFILE) {
    override fun createScreenComponent(parentComponent: RootActivity.RootComponent): Any {
        return DaggerProfileScreen_Component.builder()
                .rootComponent(parentComponent)
                .module(Module())
                .build()
    }

    override fun getLayoutResId(): Int = R.layout.screen_profile

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


