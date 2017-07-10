package io.github.rezmike.photon.ui.screens.album

import dagger.Provides
import io.github.rezmike.photon.R
import io.github.rezmike.photon.di.scopes.DaggerScope
import io.github.rezmike.photon.ui.abstracts.AbstractScreen
import io.github.rezmike.photon.ui.activities.root.BottomBarItems
import io.github.rezmike.photon.ui.activities.root.RootActivity

class AlbumScreen : AbstractScreen<RootActivity.RootComponent>() {

    override fun createScreenComponent(parentComponent: RootActivity.RootComponent): Any {
        return DaggerAlbumScreen_Component.builder()
                .rootComponent(parentComponent)
                .module(Module())
                .build()
    }

    override fun getLayoutResId(): Int = R.layout.screen_album

    override fun getCurrentBottomItem() = BottomBarItems.MAIN

    //region ======================== DI ========================

    @dagger.Component(dependencies = arrayOf(RootActivity.RootComponent::class), modules = arrayOf(Module::class))
    @DaggerScope(AlbumScreen::class)
    interface Component {
        fun inject(presenter: AlbumPresenter)

        fun inject(view: AlbumView)
    }

    @dagger.Module
    class Module {
        @Provides
        @DaggerScope(AlbumScreen::class)
        fun providePresenter() = AlbumPresenter()

        @Provides
        @DaggerScope(AlbumScreen::class)
        fun provideModel() = AlbumModel()
    }

    //endregion
}