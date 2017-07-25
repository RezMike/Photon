package io.github.rezmike.photon.ui.screens.selection.search

import dagger.Provides
import io.github.rezmike.photon.R
import io.github.rezmike.photon.di.scopes.DaggerScope
import io.github.rezmike.photon.ui.activities.root.BottomBarItems
import io.github.rezmike.photon.ui.screens.AbstractScreen
import io.github.rezmike.photon.ui.screens.selection.SelectionScreen

class SearchScreen : AbstractScreen<SelectionScreen.Component>(BottomBarItems.MAIN) {

    override fun createScreenComponent(parentComponent: SelectionScreen.Component): Any {
        return DaggerSearchScreen_Component.builder()
                .component(parentComponent)
                .module(Module())
                .build()
    }

    override fun getLayoutResId(): Int = R.layout.screen_search

    //region ======================== DI ========================

    @dagger.Component(dependencies = arrayOf(SelectionScreen.Component::class), modules = arrayOf(Module::class))
    @DaggerScope(SearchScreen::class)
    interface Component {
        fun inject(presenter: SearchPresenter)

        fun inject(view: SearchView)
    }

    @dagger.Module
    class Module {
        @Provides
        @DaggerScope(SearchScreen::class)
        fun providePresenter() = SearchPresenter()

        @Provides
        @DaggerScope(SearchScreen::class)
        fun provideModel() = SearchModel()
    }

    //endregion
}