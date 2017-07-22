package io.github.rezmike.photon.ui.screens.upload

import dagger.Provides
import io.github.rezmike.photon.R
import io.github.rezmike.photon.di.scopes.DaggerScope
import io.github.rezmike.photon.ui.activities.root.BottomBarItems
import io.github.rezmike.photon.ui.activities.root.RootActivity
import io.github.rezmike.photon.ui.screens.AbstractScreen

class UploadScreen(val albumId: String? = null) : AbstractScreen<RootActivity.RootComponent>(BottomBarItems.UPLOAD) {

    override fun createScreenComponent(parentComponent: RootActivity.RootComponent): Any {
        return DaggerUploadScreen_Component.builder()
                .rootComponent(parentComponent)
                .module(Module(albumId))
                .build()
    }

    override fun getLayoutResId(): Int = R.layout.screen_upload

    //region ======================== DI ========================

    @dagger.Component(dependencies = arrayOf(RootActivity.RootComponent::class), modules = arrayOf(Module::class))
    @DaggerScope(UploadScreen::class)
    interface Component {
        fun inject(presenter: UploadPresenter)

        fun inject(view: UploadView)
    }

    @dagger.Module
    class Module(val albumId: String?) {
        @Provides
        @DaggerScope(UploadScreen::class)
        fun providePresenter() = UploadPresenter(albumId)
    }

    //endregion
}