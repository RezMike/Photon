package io.github.rezmike.foton.ui.screens.login

import dagger.Provides
import io.github.rezmike.foton.R
import io.github.rezmike.foton.di.scopes.DaggerScope
import io.github.rezmike.foton.ui.abstracts.AbstractScreen
import io.github.rezmike.foton.ui.activities.root.RootActivity

class LoginScreen : AbstractScreen<RootActivity.RootComponent>() {

    override fun createScreenComponent(parentComponent: RootActivity.RootComponent): Any {
        return DaggerLoginScreen_Component.builder()
                .rootComponent(parentComponent)
                .module(Module())
                .build()
    }

    override fun getLayoutResId(): Int = R.layout.screen_login

    //region ======================== DI ========================

    @dagger.Component(dependencies = arrayOf(RootActivity.RootComponent::class), modules = arrayOf(Module::class))
    @DaggerScope(LoginScreen::class)
    interface Component {
        fun inject(presenter: LoginPresenter)

        fun inject(view: LoginView)
    }

    @dagger.Module
    class Module {
        @Provides
        @DaggerScope(LoginScreen::class)
        fun providePresenter() = LoginPresenter()

        @Provides
        @DaggerScope(LoginScreen::class)
        fun provideModel() = LoginModel()
    }

    //endregion
}