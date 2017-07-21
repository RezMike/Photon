package io.github.rezmike.photon.ui.screens.auth

import io.github.rezmike.photon.R
import io.github.rezmike.photon.ui.activities.root.AccountModel
import io.github.rezmike.photon.ui.screens.AbstractPresenter
import io.github.rezmike.photon.utils.DaggerService
import mortar.MortarScope

class AuthPresenter : AbstractPresenter<AuthView, AccountModel, AuthPresenter>() {

    override fun initDagger(scope: MortarScope) {
        DaggerService.getDaggerComponent<AuthScreen.Component>(scope).inject(this)
    }

    override fun initActionBar() {
        rootPresenter.ActionBarBuilder()
                .setTitle(view?.resources?.getString(R.string.auth_title)!!)
                .build()
    }

    fun onClickLogin(): Boolean {
        rootPresenter.showLoginDialog({ if (it.isOk()) rootPresenter.onClickProfile() })
        return true
    }

    fun onClickRegister(): Boolean {
        rootPresenter.showRegisterDialog({ if (it.isOk()) rootPresenter.onClickProfile() })
        return true
    }
}
