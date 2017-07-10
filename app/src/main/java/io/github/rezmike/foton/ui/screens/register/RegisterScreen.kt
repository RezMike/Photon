package io.github.rezmike.foton.ui.screens.register

import dagger.Provides
import io.github.rezmike.foton.R
import io.github.rezmike.foton.di.scopes.DaggerScope
import io.github.rezmike.foton.ui.abstracts.AbstractScreen
import io.github.rezmike.foton.ui.activities.root.BottomBarItems
import io.github.rezmike.foton.ui.activities.root.RootActivity

class RegisterScreen : AbstractScreen<RootActivity.RootComponent>() {

    override fun createScreenComponent(parentComponent: RootActivity.RootComponent): Any {
        return DaggerRegisterScreen_Component.builder()
                .rootComponent(parentComponent)
                .module(Module())
                .build()
    }

    override fun getLayoutResId(): Int = R.layout.screen_register

    override fun getCurrentBottomItem() = BottomBarItems.PROFILE

    //region ======================== DI ========================

    @dagger.Component(dependencies = arrayOf(RootActivity.RootComponent::class), modules = arrayOf(Module::class))
    @DaggerScope(RegisterScreen::class)
    interface Component {
        fun inject(presenter: RegisterPresenter)

        fun inject(view: RegisterView)
    }

    @dagger.Module
    class Module {
        @Provides
        @DaggerScope(RegisterScreen::class)
        fun providePresenter() = RegisterPresenter()

        @Provides
        @DaggerScope(RegisterScreen::class)
        fun provideModel() = RegisterModel()
    }

    //endregion
}