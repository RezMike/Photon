package io.github.rezmike.photon.ui.screens.new_album

import dagger.Provides
import io.github.rezmike.photon.R
import io.github.rezmike.photon.di.scopes.DaggerScope
import io.github.rezmike.photon.ui.abstracts.AbstractScreen
import io.github.rezmike.photon.ui.activities.root.BottomBarItems
import io.github.rezmike.photon.ui.activities.root.RootActivity

class NewAlbumScreen : AbstractScreen<RootActivity.RootComponent>() {

    override fun createScreenComponent(parentComponent: RootActivity.RootComponent): Any {
        return DaggerNewAlbumScreen_Component.builder()
                .rootComponent(parentComponent)
                .module(Module())
                .build()
    }

    override fun getLayoutResId(): Int = R.layout.screen_new_album

    override fun getCurrentBottomItem() = BottomBarItems.PROFILE

    //region ======================== DI ========================

    @dagger.Component(dependencies = arrayOf(RootActivity.RootComponent::class), modules = arrayOf(Module::class))
    @DaggerScope(NewAlbumScreen::class)
    interface Component {
        fun inject(presenter: NewAlbumPresenter)

        fun inject(view: NewAlbumView)
    }

    @dagger.Module
    class Module {
        @Provides
        @DaggerScope(NewAlbumScreen::class)
        fun providePresenter() = NewAlbumPresenter()

        @Provides
        @DaggerScope(NewAlbumScreen::class)
        fun provideModel() = NewAlbumModel()
    }

    //endregion
}