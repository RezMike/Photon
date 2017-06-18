package io.github.rezmike.foton.ui.screens.photocard

import dagger.Provides
import io.github.rezmike.foton.R
import io.github.rezmike.foton.di.scopes.DaggerScope
import io.github.rezmike.foton.ui.abstracts.AbstractScreen
import io.github.rezmike.foton.ui.root.RootActivity

class PhotocardScreen : AbstractScreen<RootActivity.RootComponent>() {

    override fun createScreenComponent(parentComponent: RootActivity.RootComponent): Any {
        return DaggerPhotocardScreen_Component.builder()
                .rootComponent(parentComponent)
                .module(Module())
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
    class Module {
        @Provides
        @DaggerScope(PhotocardScreen::class)
        fun providePresenter() = PhotocardPresenter()

        @Provides
        @DaggerScope(PhotocardScreen::class)
        fun provideModel() = PhotocardModel()
    }

    //endregion
}