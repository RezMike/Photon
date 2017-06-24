package io.github.rezmike.foton.ui.screens.main

import dagger.Provides
import io.github.rezmike.foton.R
import io.github.rezmike.foton.di.scopes.DaggerScope
import io.github.rezmike.foton.ui.abstracts.AbstractScreen
import io.github.rezmike.foton.ui.activities.root.BottomBarItems
import io.github.rezmike.foton.ui.activities.root.RootActivity

class MainScreen : AbstractScreen<RootActivity.RootComponent>() {

    override fun createScreenComponent(parentComponent: RootActivity.RootComponent): Any {
        return DaggerMainScreen_Component.builder()
                .rootComponent(parentComponent)
                .module(Module())
                .build()
    }

    override fun getLayoutResId(): Int = R.layout.screen_main

    override fun getCurrentBottomItem() = BottomBarItems.MAIN

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