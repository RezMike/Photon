package io.github.rezmike.foton.ui.screens.add_info

import dagger.Provides
import io.github.rezmike.foton.R
import io.github.rezmike.foton.di.scopes.DaggerScope
import io.github.rezmike.foton.ui.abstracts.AbstractScreen
import io.github.rezmike.foton.ui.activities.root.BottomBarItems
import io.github.rezmike.foton.ui.activities.root.RootActivity

class AddInfoScreen(photoUri: String) : AbstractScreen<RootActivity.RootComponent>() {

    override fun createScreenComponent(parentComponent: RootActivity.RootComponent): Any {
        return DaggerAddInfoScreen_Component.builder()
                .rootComponent(parentComponent)
                .module(Module())
                .build()
    }

    override fun getLayoutResId(): Int = R.layout.screen_add_info

    override fun getCurrentBottomItem() = BottomBarItems.UPLOAD

    //region ======================== DI ========================

    @dagger.Component(dependencies = arrayOf(RootActivity.RootComponent::class), modules = arrayOf(Module::class))
    @DaggerScope(AddInfoScreen::class)
    interface Component {
        fun inject(presenter: AddInfoPresenter)

        fun inject(view: AddInfoView)
    }

    @dagger.Module
    class Module {
        @Provides
        @DaggerScope(AddInfoScreen::class)
        fun providePresenter() = AddInfoPresenter()

        @Provides
        @DaggerScope(AddInfoScreen::class)
        fun provideModel() = AddInfoModel()
    }

    //endregion
}