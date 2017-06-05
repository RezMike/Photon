package io.github.rezmike.foton.ui.screens.profile

import dagger.Provides
import io.github.rezmike.foton.R
import io.github.rezmike.foton.di.scopes.DaggerScope
import io.github.rezmike.foton.ui.abstracts.AbstractScreen
import io.github.rezmike.foton.ui.root.RootActivity
import io.github.rezmike.foton.ui.screens.navigation.NavigationModel
import io.github.rezmike.foton.ui.screens.navigation.NavigationPresenter
import io.github.rezmike.foton.ui.screens.navigation.NavigationScreen

class ProfileScreen : AbstractScreen<NavigationScreen.Component>() {
    override fun createScreenComponent(parentComponent: NavigationScreen.Component): Any {
        return DaggerProfileScreen_Component.builder()
                .component(parentComponent)
                .module(Module())
                .build()
    }

    override fun getLayoutResId(): Int = R.layout.screen_profile

    //    region ======================== DI ========================
    @dagger.Component(dependencies = arrayOf(NavigationScreen.Component::class), modules = arrayOf(Module::class))
    @DaggerScope(ProfileScreen::class)
    interface Component {
        fun inject(presenter: ProfilePresenter)

        fun inject(view: ProfileView)
    }

    @dagger.Module
    class Module {

        @Provides
        @DaggerScope(ProfileScreen::class)
        internal fun providePresenter() = ProfilePresenter()

        @Provides
        @DaggerScope(ProfileScreen::class)
        internal fun provideSplashModel() = ProfileModel()

    }
//    endregion
}


