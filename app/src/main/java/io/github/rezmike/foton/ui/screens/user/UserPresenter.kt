package io.github.rezmike.foton.ui.screens.user

import io.github.rezmike.foton.ui.abstracts.AbstractPresenter
import io.github.rezmike.foton.utils.DaggerService
import mortar.MortarScope

class UserPresenter(val userId: String) : AbstractPresenter<UserView, UserModel, UserPresenter>() {

    override fun initDagger(scope: MortarScope) {
        DaggerService.getDaggerComponent<UserScreen.Component>(scope).inject(this)
    }

    override fun initActionBar() {

    }
}
