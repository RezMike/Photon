package io.github.rezmike.foton.ui.screens.login

import io.github.rezmike.foton.ui.abstracts.AbstractPresenter
import io.github.rezmike.foton.utils.DaggerService
import mortar.MortarScope

class LoginPresenter : AbstractPresenter<LoginView, LoginModel, LoginPresenter>() {

    override fun initDagger(scope: MortarScope) {
        DaggerService.getDaggerComponent<LoginScreen.Component>(scope).inject(this)
    }

    override fun initActionBar() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
