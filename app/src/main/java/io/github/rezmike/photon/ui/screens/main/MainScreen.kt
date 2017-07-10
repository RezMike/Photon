package io.github.rezmike.photon.ui.screens.main

import dagger.Provides
import io.github.rezmike.photon.R
import io.github.rezmike.photon.di.scopes.DaggerScope
import io.github.rezmike.photon.ui.abstracts.AbstractScreen
import io.github.rezmike.photon.ui.activities.root.BottomBarItems
import io.github.rezmike.photon.ui.activities.root.RootActivity

class MainScreen : AbstractScreen<RootActivity.RootComponent>(BottomBarItems.MAIN) {

    override fun createScreenComponent(parentComponent: RootActivity.RootComponent): Any {
        return DaggerMainScreen_Component.builder()
                .rootComponent(parentComponent)
                .module(Module())
                .build()
    }

    override fun getLayoutResId(): Int = R.layout.screen_main

    //region ======================== DI ========================

    @dagger.Component(dependencies = arrayOf(RootActivity.RootComponent::class), modules = arrayOf(Module::class))
    @DaggerScope(MainScreen::class)
    interface Component {
        fun inject(presenter: MainPresenter)

        fun inject(view: MainView)
    }

    @dagger.Module
    class Module {
        @Provides
        @DaggerScope(MainScreen::class)
        fun providePresenter() = MainPresenter()

        @Provides
        @DaggerScope(MainScreen::class)
        fun provideModel() = MainModel()
    }

    //endregion
}