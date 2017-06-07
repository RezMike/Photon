package io.github.rezmike.foton.ui.screens.photocard

import io.github.rezmike.foton.ui.abstracts.AbstractPresenter
import io.github.rezmike.foton.utils.DaggerService
import mortar.MortarScope

class PhotocardPresenter : AbstractPresenter<PhotocardView, PhotocardModel, PhotocardPresenter>() {

    override fun initDagger(scope: MortarScope) {
        DaggerService.getDaggerComponent<PhotocardScreen.Component>(scope).inject(this)
    }
}
