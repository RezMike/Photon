package io.github.rezmike.photon.ui.screens.profile

import io.github.rezmike.photon.ui.abstracts.AbstractPresenter
import io.github.rezmike.photon.utils.DaggerService
import mortar.MortarScope

class ProfilePresenter : AbstractPresenter<ProfileView, ProfileModel, ProfilePresenter>() {

    override fun initDagger(scope: MortarScope) {
        DaggerService.getDaggerComponent<ProfileScreen.Component>(scope).inject(this)
    }

    override fun initActionBar() {
        rootPresenter.ActionBarBuilder()
                .setTitle("Профиль")
                .build()
    }
}