package io.github.rezmike.foton.ui.screens.profile

import io.github.rezmike.foton.mortar.ScreenScoper
import io.github.rezmike.foton.ui.abstracts.AbstractPresenter
import mortar.MortarScope

class ProfilePresenter : AbstractPresenter<ProfileView, ProfileModel, ProfilePresenter>() {

    override fun initDagger(scope: MortarScope?) {
        ScreenScoper.getDaggerComponent<ProfileScreen.Component>(scope!!).inject(this)
    }
}