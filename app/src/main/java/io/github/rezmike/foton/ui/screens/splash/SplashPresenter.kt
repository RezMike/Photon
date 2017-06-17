package io.github.rezmike.foton.ui.screens.splash

import io.github.rezmike.foton.ui.abstracts.AbstractPresenter
import io.github.rezmike.foton.utils.DaggerService
import mortar.MortarScope

class SplashPresenter : AbstractPresenter<SplashView, SplashModel, SplashPresenter>() {

    override fun initDagger(scope: MortarScope) {
        DaggerService.getDaggerComponent<SplashScreen.Component>(scope).inject(this)
    }

    override fun initActionBar() {
        rootPresenter.newActionBarBuilder()
                .setVisible(false)
                .build()
    }

    fun init() {
        model.updateLocalDataObs()
                .subscribe({ view?.showMainScreen() }, {
                    // TODO: 14.06.2017 show error in RootActivity
                })
    }
}
