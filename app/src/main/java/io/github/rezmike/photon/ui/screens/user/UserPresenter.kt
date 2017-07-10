package io.github.rezmike.photon.ui.screens.user

import io.github.rezmike.photon.ui.abstracts.AbstractPresenter
import io.github.rezmike.photon.utils.DaggerService
import mortar.MortarScope

class UserPresenter(val userId: String) : AbstractPresenter<UserView, UserModel, UserPresenter>() {

    override fun initDagger(scope: MortarScope) {
        DaggerService.getDaggerComponent<UserScreen.Component>(scope).inject(this)
    }

    override fun initActionBar() {

    }
}
