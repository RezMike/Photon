package io.github.rezmike.photon.ui.screens.album

import io.github.rezmike.photon.ui.abstracts.AbstractPresenter
import io.github.rezmike.photon.utils.DaggerService
import mortar.MortarScope

class AlbumPresenter : AbstractPresenter<AlbumView, AlbumModel, AlbumPresenter>() {

    override fun initDagger(scope: MortarScope) {
        DaggerService.getDaggerComponent<AlbumScreen.Component>(scope).inject(this)
    }

    override fun initActionBar() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
