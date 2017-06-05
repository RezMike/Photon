package io.github.rezmike.foton.ui.root

import io.github.rezmike.foton.mortar.ScreenScoper
import mortar.MortarScope
import mortar.Presenter
import mortar.bundler.BundleService

class RootPresenter : Presenter<RootActivity>() {

    override fun onEnterScope(scope: MortarScope) {
        super.onEnterScope(scope)
        ScreenScoper.getDaggerComponent<RootActivity.RootComponent>(scope).inject(this)
    }

    override fun extractBundleService(view: RootActivity): BundleService {
        return BundleService.getBundleService(view)
    }
}