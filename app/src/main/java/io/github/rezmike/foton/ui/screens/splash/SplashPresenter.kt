package io.github.rezmike.foton.ui.screens.splash

import io.github.rezmike.foton.ui.abstracts.AbstractPresenter
import io.github.rezmike.foton.utils.DaggerService
import mortar.MortarScope

class SplashPresenter : AbstractPresenter<SplashView, SplashModel, SplashPresenter>() {

    override fun initDagger(scope: MortarScope) {
        DaggerService.getDaggerComponent<SplashScreen.Component>(scope).inject(this)
    }


}
