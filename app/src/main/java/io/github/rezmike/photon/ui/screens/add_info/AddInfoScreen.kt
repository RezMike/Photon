package io.github.rezmike.photon.ui.screens.add_info

import dagger.Provides
import io.github.rezmike.photon.R
import io.github.rezmike.photon.di.scopes.DaggerScope
import io.github.rezmike.photon.ui.activities.root.BottomBarItems
import io.github.rezmike.photon.ui.activities.root.RootActivity
import io.github.rezmike.photon.ui.screens.AbstractScreen

class AddInfoScreen(val photoUri: String, val albumId: String? = null) : AbstractScreen<RootActivity.RootComponent>(BottomBarItems.UPLOAD) {

    override fun createScreenComponent(parentComponent: RootActivity.RootComponent): Any {
        return DaggerAddInfoScreen_Component.builder()
                .rootComponent(parentComponent)
                .module(Module(photoUri, albumId))
                .build()
    }

    override fun getLayoutResId(): Int = R.layout.screen_add_info

    //region ======================== DI ========================

    @dagger.Component(dependencies = arrayOf(RootActivity.RootComponent::class), modules = arrayOf(Module::class))
    @DaggerScope(AddInfoScreen::class)
    interface Component {
        fun inject(presenter: AddInfoPresenter)

        fun inject(view: AddInfoView)
    }

    @dagger.Module
    class Module(val photoUri: String, val albumId: String?) {
        @Provides
        @DaggerScope(AddInfoScreen::class)
        fun providePresenter() = AddInfoPresenter(photoUri, albumId)

        @Provides
        @DaggerScope(AddInfoScreen::class)
        fun provideModel() = AddInfoModel()
    }

    //endregion
}