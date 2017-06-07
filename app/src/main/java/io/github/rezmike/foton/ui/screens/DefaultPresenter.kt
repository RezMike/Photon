package io.github.rezmike.foton.ui.screens

import io.github.rezmike.foton.ui.abstracts.AbstractPresenter
import io.github.rezmike.foton.utils.DaggerService
import mortar.MortarScope

class DefaultPresenter : AbstractPresenter<DefaultView, DefaultModel, DefaultPresenter>() {

    override fun initDagger(scope: MortarScope) {
        DaggerService.getDaggerComponent<DefaultScreen.Component>(scope).inject(this)
    }
}
