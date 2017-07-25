package io.github.rezmike.photon.ui.screens.selection

import dagger.Provides
import io.github.rezmike.photon.R
import io.github.rezmike.photon.di.scopes.DaggerScope
import io.github.rezmike.photon.ui.activities.root.BottomBarItems
import io.github.rezmike.photon.ui.activities.root.RootActivity
import io.github.rezmike.photon.ui.activities.root.RootPresenter
import io.github.rezmike.photon.ui.screens.AbstractScreen

class SelectionScreen : AbstractScreen<RootActivity.RootComponent>(BottomBarItems.MAIN) {

    override fun createScreenComponent(parentComponent: RootActivity.RootComponent): Any {
        return DaggerSelectionScreen_Component.builder()
                .rootComponent(parentComponent)
                .module(Module())
                .build()
    }

    override fun getLayoutResId(): Int = R.layout.screen_selection

    //region ======================== DI ========================

    @dagger.Component(dependencies = arrayOf(RootActivity.RootComponent::class), modules = arrayOf(Module::class))
    @DaggerScope(SelectionScreen::class)
    interface Component {
        fun inject(presenter: SelectionPresenter)

        fun inject(view: SelectionView)

        fun getRootPresenter(): RootPresenter
    }

    @dagger.Module
    class Module {
        @Provides
        @DaggerScope(SelectionScreen::class)
        fun providePresenter() = SelectionPresenter()
    }

    //endregion
}