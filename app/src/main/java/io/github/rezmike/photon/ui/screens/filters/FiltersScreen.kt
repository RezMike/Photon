package io.github.rezmike.photon.ui.screens.filters

import dagger.Provides
import io.github.rezmike.photon.R
import io.github.rezmike.photon.di.scopes.DaggerScope
import io.github.rezmike.photon.ui.abstracts.AbstractScreen
import io.github.rezmike.photon.ui.activities.root.BottomBarItems
import io.github.rezmike.photon.ui.activities.root.RootActivity

class FiltersScreen : AbstractScreen<RootActivity.RootComponent>(BottomBarItems.MAIN) {

    override fun createScreenComponent(parentComponent: RootActivity.RootComponent): Any {
        return DaggerFiltersScreen_Component.builder()
                .rootComponent(parentComponent)
                .module(Module())
                .build()
    }

    override fun getLayoutResId(): Int = R.layout.layout_filters

    //region ======================== DI ========================

    @dagger.Component(dependencies = arrayOf(RootActivity.RootComponent::class), modules = arrayOf(Module::class))
    @DaggerScope(FiltersScreen::class)
    interface Component {
        fun inject(presenter: FiltersPresenter)

        fun inject(view: FiltersView)
    }

    @dagger.Module
    class Module {
        @Provides
        @DaggerScope(FiltersScreen::class)
        fun providePresenter() = FiltersPresenter()

        @Provides
        @DaggerScope(FiltersScreen::class)
        fun provideModel() = FiltersModel()
    }

    //endregion
}