package io.github.rezmike.foton.ui.screens.navigation

import dagger.Provides
import io.github.rezmike.foton.R
import io.github.rezmike.foton.di.scopes.DaggerScope
import io.github.rezmike.foton.ui.abstracts.AbstractScreen
import io.github.rezmike.foton.ui.root.RootActivity

class NavigationScreen :AbstractScreen<RootActivity.RootComponent>(){
    override fun createScreenComponent(parentComponent: RootActivity.RootComponent): Any {
        return DaggerNavigationScreen_Component.builder()
                .rootComponent(parentComponent)
                .module(Module())
                .build()
    }

    override fun getLayoutResId(): Int = R.layout.screen_navigation

    //region ======================== DI ========================
    @dagger.Component(dependencies = arrayOf(RootActivity.RootComponent::class), modules = arrayOf(Module::class))
    @DaggerScope(NavigationScreen::class)
    interface Component {
        fun inject(presenter: NavigationPresenter)

        fun inject(view: NavigationView)
    }

    @dagger.Module
    class Module {

        @Provides
        @DaggerScope(NavigationScreen::class)
        internal fun providePresenter() = NavigationPresenter()

        @Provides
        @DaggerScope(NavigationScreen::class)
        internal fun provideSplashModel() = NavigationModel()

    }
    //endregion
}