package io.github.rezmike.foton.ui.screens.splash

import dagger.Provides
import io.github.rezmike.foton.R
import io.github.rezmike.foton.di.scopes.DaggerScope
import io.github.rezmike.foton.ui.abstracts.AbstractScreen
import io.github.rezmike.foton.ui.root.RootActivity
import io.github.rezmike.foton.ui.screens.navigation.NavigationScreen

class SplashScreen : AbstractScreen<RootActivity.RootComponent>() {

    override fun createScreenComponent(parentComponent: RootActivity.RootComponent): Any {
        return DaggerSplashScreen_Component.builder()
                .rootComponent(parentComponent)
                .module(Module())
                .build()
    }

    override fun getLayoutResId(): Int = R.layout.screen_splash

    //region ======================== DI ========================

    @dagger.Component(dependencies = arrayOf(RootActivity.RootComponent::class), modules = arrayOf(Module::class))
    @DaggerScope(SplashPresenter::class)
    interface Component {
        fun inject(presenter: SplashPresenter)

        fun inject(view: SplashView)
    }


    @dagger.Module
    class Module {

        @Provides
        @DaggerScope(SplashPresenter::class)
        internal fun providePresenter() = SplashPresenter()

        @Provides
        @DaggerScope(SplashPresenter::class)
        internal fun provideSplashModel() = SplashModel()

    }
    //endregion
}