package io.github.rezmike.foton.ui.screens.upload

import dagger.Provides
import io.github.rezmike.foton.R
import io.github.rezmike.foton.di.scopes.DaggerScope
import io.github.rezmike.foton.ui.abstracts.AbstractScreen
import io.github.rezmike.foton.ui.activities.root.BottomBarItems
import io.github.rezmike.foton.ui.activities.root.RootActivity

class UploadScreen : AbstractScreen<RootActivity.RootComponent>() {

    override fun createScreenComponent(parentComponent: RootActivity.RootComponent): Any {
        return DaggerUploadScreen_Component.builder()
                .rootComponent(parentComponent)
                .module(Module())
                .build()
    }

    override fun getLayoutResId(): Int = R.layout.screen_upload

    override fun getCurrentBottomItem() = BottomBarItems.UPLOAD

    //region ======================== DI ========================

    @dagger.Component(dependencies = arrayOf(RootActivity.RootComponent::class), modules = arrayOf(Module::class))
    @DaggerScope(UploadScreen::class)
    interface Component {
        fun inject(presenter: UploadPresenter)

        fun inject(view: UploadView)
    }

    @dagger.Module
    class Module {
        @Provides
        @DaggerScope(UploadScreen::class)
        fun providePresenter() = UploadPresenter()

        @Provides
        @DaggerScope(UploadScreen::class)
        fun provideModel() = UploadModel()
    }

    //endregion
}