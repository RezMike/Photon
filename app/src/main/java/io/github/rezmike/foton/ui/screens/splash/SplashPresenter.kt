package io.github.rezmike.foton.ui.screens.splash

import dagger.Provides
import io.github.rezmike.foton.di.scopes.DaggerScope
import io.github.rezmike.foton.ui.abstracts.AbstractPresenter
import io.github.rezmike.foton.ui.root.RootActivity

class SplashPresenter : AbstractPresenter<SplashView, SplashModel>() {


    @dagger.Module
    class Module {

        @Provides
        @DaggerScope(SplashPresenter::class)
        internal fun providePresenter() = SplashPresenter()

        @Provides
        @DaggerScope(SplashPresenter::class)
        internal fun provideSplashModel() = SplashModel()
    }

    @dagger.Component(dependencies = arrayOf(RootActivity.RootComponent::class), modules = arrayOf(Module::class))
    @DaggerScope(SplashPresenter::class)
    interface Component {
        fun inject(presenter: SplashPresenter)

        fun inject(view: SplashView)
    }
}
