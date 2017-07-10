package io.github.rezmike.foton.ui.activities.splash

import io.github.rezmike.foton.utils.DaggerService
import mortar.MortarScope
import mortar.Presenter
import mortar.bundler.BundleService
import rx.Completable
import rx.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SplashPresenter : Presenter<SplashActivity>() {

    @Inject
    lateinit var model: SplashModel

    override fun extractBundleService(view: SplashActivity?): BundleService {
        return BundleService.getBundleService(view)
    }

    override fun onEnterScope(scope: MortarScope) {
        super.onEnterScope(scope)
        DaggerService.getDaggerComponent<SplashActivity.SplashComponent>(scope).inject(this)
    }

    fun init() {
        view?.showProgress()
        Completable.merge(model.updateLocalDataCompl(), Completable.timer(3, TimeUnit.SECONDS))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view?.hideProgress()
                    view?.showRootActivity()
                }, {
                    view?.hideProgress()
                    view?.showError(it)
                })
    }
}