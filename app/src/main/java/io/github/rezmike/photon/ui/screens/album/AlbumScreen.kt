package io.github.rezmike.photon.ui.screens.album

import dagger.Provides
import io.github.rezmike.photon.R
import io.github.rezmike.photon.data.storage.realm.AlbumRealm
import io.github.rezmike.photon.di.scopes.DaggerScope
import io.github.rezmike.photon.ui.activities.root.BottomBarItems
import io.github.rezmike.photon.ui.activities.root.RootActivity
import io.github.rezmike.photon.ui.screens.AbstractScreen

class AlbumScreen(val albumRealm: AlbumRealm, val profile: BottomBarItems) : AbstractScreen<RootActivity.RootComponent>(BottomBarItems.MAIN) {

    override fun createScreenComponent(parentComponent: RootActivity.RootComponent): Any {
        return DaggerAlbumScreen_Component.builder()
                .rootComponent(parentComponent)
                .module(Module(albumRealm))
                .build()
    }

    override fun getLayoutResId(): Int = R.layout.screen_album

    //region ======================== DI ========================

    @dagger.Component(dependencies = arrayOf(RootActivity.RootComponent::class), modules = arrayOf(Module::class))
    @DaggerScope(AlbumScreen::class)
    interface Component {
        fun inject(presenter: AlbumPresenter)

        fun inject(view: AlbumView)
    }

    @dagger.Module
    class Module(val albumRealm: AlbumRealm) {
        @Provides
        @DaggerScope(AlbumScreen::class)
        fun providePresenter() = AlbumPresenter(albumRealm)

        @Provides
        @DaggerScope(AlbumScreen::class)
        fun provideModel() = AlbumModel()
    }

    //endregion
}