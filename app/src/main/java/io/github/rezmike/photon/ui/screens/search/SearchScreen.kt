package io.github.rezmike.photon.ui.screens.search

import dagger.Provides
import io.github.rezmike.photon.R
import io.github.rezmike.photon.di.scopes.DaggerScope
import io.github.rezmike.photon.ui.activities.root.BottomBarItems
import io.github.rezmike.photon.ui.activities.root.RootActivity
import io.github.rezmike.photon.ui.screens.AbstractScreen

class SearchScreen : AbstractScreen<RootActivity.RootComponent>(BottomBarItems.MAIN) {

    override fun createScreenComponent(parentComponent: RootActivity.RootComponent): Any {
        return DaggerSearchScreen_Component.builder()
                .rootComponent(parentComponent)
                .module(Module())
                .build()
    }

    override fun getLayoutResId(): Int = R.layout.screen_search

    //region ======================== DI ========================

    @dagger.Component(dependencies = arrayOf(RootActivity.RootComponent::class), modules = arrayOf(Module::class))
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