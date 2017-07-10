package io.github.rezmike.foton.ui.screens.photocard

import dagger.Provides
import io.github.rezmike.foton.R
import io.github.rezmike.foton.data.storage.realm.PhotoCardRealm
import io.github.rezmike.foton.di.scopes.DaggerScope
import io.github.rezmike.foton.ui.abstracts.AbstractScreen
import io.github.rezmike.foton.ui.activities.root.BottomBarItems
import io.github.rezmike.foton.ui.activities.root.RootActivity

class PhotocardScreen(val photoCard: PhotoCardRealm) : AbstractScreen<RootActivity.RootComponent>() {

    override fun createScreenComponent(parentComponent: RootActivity.RootComponent): Any {
        return DaggerPhotocardScreen_Component.builder()
                .rootComponent(parentComponent)
                .module(Module(photoCard))
                .build()
    }

    override fun getLayoutResId(): Int = R.layout.screen_photocard

    override fun getCurrentBottomItem() = BottomBarItems.MAIN

    //region ======================== DI ========================

    @dagger.Component(dependencies = arrayOf(RootActivity.RootComponent::class), modules = arrayOf(Module::class))
    @DaggerScope(PhotocardScreen::class)
    interface Component {
        fun inject(presenter: PhotocardPresenter)

        fun inject(view: PhotocardView)
    }

    @dagger.Module
    class Module(val photoCard: PhotoCardRealm) {

        @Provides
        @DaggerScope(PhotocardScreen::class)
        fun providePresenter() = PhotocardPresenter(photoCard)

        @Provides
        @DaggerScope(PhotocardScreen::class)
        fun provideModel() = PhotocardModel()
    }

    //endregion
}