package io.github.rezmike.foton.ui.screens.splash

import io.github.rezmike.foton.mortar.ScreenScoper
import io.github.rezmike.foton.ui.abstracts.AbstractPresenter
import mortar.MortarScope

class SplashPresenter : AbstractPresenter<SplashView, SplashModel, SplashPresenter>() {

    override fun initDagger(scope: MortarScope?) {
        ScreenScoper.getDaggerComponent<SplashScreen.Component>(scope!!).inject(this)
    }


}
