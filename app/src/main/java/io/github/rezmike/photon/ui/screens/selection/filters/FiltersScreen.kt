package io.github.rezmike.photon.ui.screens.selection.filters

import dagger.Provides
import io.github.rezmike.photon.R
import io.github.rezmike.photon.di.scopes.DaggerScope
import io.github.rezmike.photon.ui.activities.root.BottomBarItems
import io.github.rezmike.photon.ui.screens.AbstractScreen
import io.github.rezmike.photon.ui.screens.selection.SelectionScreen

class FiltersScreen : AbstractScreen<SelectionScreen.Component>(BottomBarItems.MAIN) {

    override fun createScreenComponent(parentComponent: SelectionScreen.Component): Any {
        return DaggerFiltersScreen_Component.builder()
                .component(parentComponent)
                .module(Module())
                .build()
    }

    override fun getLayoutResId(): Int = R.layout.layout_filters

    //region ======================== DI ========================

    @dagger.Component(dependencies = arrayOf(SelectionScreen.Component::class), modules = arrayOf(Module::class))
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