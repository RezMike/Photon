package io.github.rezmike.photon.ui.screens.register

import io.github.rezmike.photon.ui.abstracts.AbstractPresenter
import io.github.rezmike.photon.utils.DaggerService
import mortar.MortarScope

class RegisterPresenter : AbstractPresenter<RegisterView, RegisterModel, RegisterPresenter>() {

    override fun initDagger(scope: MortarScope) {
        DaggerService.getDaggerComponent<RegisterScreen.Component>(scope).inject(this)
    }

    override fun initActionBar() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
