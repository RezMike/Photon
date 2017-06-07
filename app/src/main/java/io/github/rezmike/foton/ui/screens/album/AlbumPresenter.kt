package io.github.rezmike.foton.ui.screens.album

import io.github.rezmike.foton.ui.abstracts.AbstractPresenter
import io.github.rezmike.foton.utils.DaggerService
import mortar.MortarScope

class AlbumPresenter : AbstractPresenter<AlbumView, AlbumModel, AlbumPresenter>() {

    override fun initDagger(scope: MortarScope) {
        DaggerService.getDaggerComponent<AlbumScreen.Component>(scope).inject(this)
    }
}
