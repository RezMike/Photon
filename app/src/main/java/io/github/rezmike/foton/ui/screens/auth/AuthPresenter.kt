package io.github.rezmike.foton.ui.screens.auth

import flow.Flow
import io.github.rezmike.foton.R
import io.github.rezmike.foton.ui.abstracts.AbstractPresenter
import io.github.rezmike.foton.ui.screens.register.RegisterScreen
import io.github.rezmike.foton.utils.DaggerService
import mortar.MortarScope

class AuthPresenter : AbstractPresenter<AuthView, AuthModel, AuthPresenter>() {

    override fun initDagger(scope: MortarScope) {
        DaggerService.getDaggerComponent<AuthScreen.Component>(scope).inject(this)
    }

    override fun initActionBar() {
        rootPresenter.ActionBarBuilder()
                .setTitle(view?.resources?.getString(R.string.auth_title)!!)
                .build()
    }

    fun onClickLogin(): Boolean {
        rootPresenter.showLoginDialog()
        return true
    }

    fun onClickRegister(): Boolean {
        Flow.get(view).set(RegisterScreen())
        return true
    }
}
