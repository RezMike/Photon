package io.github.rezmike.photon.ui.screens.new_album

import io.github.rezmike.photon.ui.abstracts.AbstractPresenter
import io.github.rezmike.photon.utils.DaggerService
import mortar.MortarScope

class NewAlbumPresenter : AbstractPresenter<NewAlbumView, NewAlbumModel, NewAlbumPresenter>() {

    override fun initDagger(scope: MortarScope) {
        DaggerService.getDaggerComponent<NewAlbumScreen.Component>(scope).inject(this)
    }

    override fun initActionBar() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
