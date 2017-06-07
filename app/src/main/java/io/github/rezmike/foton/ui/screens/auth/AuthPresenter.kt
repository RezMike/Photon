package io.github.rezmike.foton.ui.screens.auth

import io.github.rezmike.foton.ui.abstracts.AbstractPresenter
import io.github.rezmike.foton.utils.DaggerService
import mortar.MortarScope

class AuthPresenter : AbstractPresenter<AuthView, AuthModel, AuthPresenter>() {

    override fun initDagger(scope: MortarScope) {
        DaggerService.getDaggerComponent<AuthScreen.Component>(scope).inject(this)
    }
}
