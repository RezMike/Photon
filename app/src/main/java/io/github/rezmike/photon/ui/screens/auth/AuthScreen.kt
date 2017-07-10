package io.github.rezmike.photon.ui.screens.auth

import dagger.Provides
import io.github.rezmike.photon.R
import io.github.rezmike.photon.di.scopes.DaggerScope
import io.github.rezmike.photon.ui.abstracts.AbstractScreen
import io.github.rezmike.photon.ui.activities.root.BottomBarItems
import io.github.rezmike.photon.ui.activities.root.RootActivity

class AuthScreen : AbstractScreen<RootActivity.RootComponent>(BottomBarItems.PROFILE) {

    override fun createScreenComponent(parentComponent: RootActivity.RootComponent): Any {
        return DaggerAuthScreen_Component.builder()
                .rootComponent(parentComponent)
                .module(Module())
                .build()
    }

    override fun getLayoutResId(): Int = R.layout.screen_auth

    //region ======================== DI ========================

    @dagger.Component(dependencies = arrayOf(RootActivity.RootComponent::class), modules = arrayOf(Module::class))
    @DaggerScope(AuthScreen::class)
    interface Component {
        fun inject(presenter: AuthPresenter)

        fun inject(view: AuthView)
    }

    @dagger.Module
    class Module {
        @Provides
        @DaggerScope(AuthScreen::class)
        fun providePresenter() = AuthPresenter()
    }

    //endregion
}