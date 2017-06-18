package io.github.rezmike.foton.ui.screens.splash

import android.os.Handler
import io.github.rezmike.foton.ui.abstracts.AbstractPresenter
import io.github.rezmike.foton.utils.DaggerService
import mortar.MortarScope

class SplashPresenter : AbstractPresenter<SplashView, SplashModel, SplashPresenter>() {

    private var isUpdated = false
    private var isCounted = false

    override fun initDagger(scope: MortarScope) {
        DaggerService.getDaggerComponent<SplashScreen.Component>(scope).inject(this)
    }

    override fun initActionBar() {
        rootPresenter.newActionBarBuilder()
                .setVisible(false)
                .build()
    }

    fun init() {
        Handler().postDelayed({ onCountFinished() }, 3000)
        model.updateLocalDataCompl()
                .subscribe({ onUpdateFinished() }, { rootPresenter.getRootView()?.showError(it) })
    }

    private fun onCountFinished() {
        isCounted = true
        checkProgress()
    }

    private fun onUpdateFinished() {
        isUpdated = true
        checkProgress()
    }

    private fun checkProgress() {
        if (isUpdated && isCounted) view?.showMainScreen()
    }
}
