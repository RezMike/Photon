package io.github.rezmike.foton.ui.activities.splash

import io.github.rezmike.foton.utils.DaggerService
import mortar.MortarScope
import mortar.Presenter
import mortar.bundler.BundleService
import rx.Completable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SplashPresenter : Presenter<SplashActivity>() {

    @Inject
    protected lateinit var model: SplashModel

    override fun extractBundleService(view: SplashActivity?): BundleService {
        return BundleService.getBundleService(view)
    }

    override fun onEnterScope(scope: MortarScope) {
        super.onEnterScope(scope)
        DaggerService.getDaggerComponent<SplashActivity.SplashComponent>(scope).inject(this)
    }

    //region ======================== Presenter ========================

    fun init() {
        Completable.merge(model.updateLocalDataCompl(), delayCompl())
                .subscribe({ view?.showRootActivity() }, { view?.showError(it) })
    }

    private fun delayCompl() = Completable.timer(3, TimeUnit.SECONDS)

    //endregion
}