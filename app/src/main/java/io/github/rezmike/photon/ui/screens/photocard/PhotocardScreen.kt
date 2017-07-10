package io.github.rezmike.photon.ui.screens.photocard

import dagger.Provides
import io.github.rezmike.photon.R
import io.github.rezmike.photon.data.storage.realm.PhotoCardRealm
import io.github.rezmike.photon.di.scopes.DaggerScope
import io.github.rezmike.photon.ui.abstracts.AbstractScreen
import io.github.rezmike.photon.ui.activities.root.BottomBarItems
import io.github.rezmike.photon.ui.activities.root.RootActivity

class PhotocardScreen(val photoCard: PhotoCardRealm,
                      val bottomBarItem: BottomBarItems = BottomBarItems.MAIN) : AbstractScreen<RootActivity.RootComponent>(bottomBarItem) {

    override fun createScreenComponent(parentComponent: RootActivity.RootComponent): Any {
        return DaggerPhotocardScreen_Component.builder()
                .rootComponent(parentComponent)
                .module(Module(photoCard, bottomBarItem))
                .build()
    }

    override fun getLayoutResId(): Int = R.layout.screen_photocard

    //region ======================== DI ========================

    @dagger.Component(dependencies = arrayOf(RootActivity.RootComponent::class), modules = arrayOf(Module::class))
    @DaggerScope(PhotocardScreen::class)
    interface Component {
        fun inject(presenter: PhotocardPresenter)

        fun inject(view: PhotocardView)
    }

    @dagger.Module
    class Module(val photoCard: PhotoCardRealm, val bottomBarItem: BottomBarItems) {

        @Provides
        @DaggerScope(PhotocardScreen::class)
        fun providePresenter() = PhotocardPresenter(photoCard, bottomBarItem)

        @Provides
        @DaggerScope(PhotocardScreen::class)
        fun provideModel() = PhotocardModel()
    }

    //endregion
}