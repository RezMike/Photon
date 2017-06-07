package io.github.rezmike.foton.ui.screens

import dagger.Provides
import io.github.rezmike.foton.R
import io.github.rezmike.foton.di.scopes.DaggerScope
import io.github.rezmike.foton.ui.abstracts.AbstractScreen
import io.github.rezmike.foton.ui.root.RootActivity
import io.github.rezmike.foton.utils.ScreenScoper

class DefaultScreen : AbstractScreen<RootActivity.RootComponent>() {

    override fun createScreenComponent(parentComponent: RootActivity.RootComponent): Any {
        return ScreenScoper
    }

    override fun getLayoutResId(): Int = R.layout.screen_main

    //region ======================== DI ========================

    @dagger.Component(dependencies = arrayOf(RootActivity.RootComponent::class), modules = arrayOf(Module::class))
    @DaggerScope(DefaultScreen::class)
    interface Component {
        fun inject(presenter: DefaultPresenter)

        fun inject(view: DefaultView)
    }

    @dagger.Module
    class Module {
        @Provides
        @DaggerScope(DefaultScreen::class)
        fun providePresenter() = DefaultPresenter()

        @Provides
        @DaggerScope(DefaultScreen::class)
        fun provideModel() = DefaultModel()
    }

    //endregion
}