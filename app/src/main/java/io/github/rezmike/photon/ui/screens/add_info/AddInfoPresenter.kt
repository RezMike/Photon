package io.github.rezmike.photon.ui.screens.add_info

import android.os.Bundle
import io.github.rezmike.photon.R
import io.github.rezmike.photon.ui.screens.AbstractPresenter
import io.github.rezmike.photon.utils.DaggerService
import mortar.MortarScope

class AddInfoPresenter(val photoUri: String, var albumIdSelected: String?) : AbstractPresenter<AddInfoView, AddInfoModel, AddInfoPresenter>() {

    override fun initDagger(scope: MortarScope) {
        DaggerService.getDaggerComponent<AddInfoScreen.Component>(scope).inject(this)
    }

    override fun initActionBar() {
        rootPresenter.ActionBarBuilder()
                .setBackArrow(true)
                .setTitle(view.context.getString(R.string.add_info_title))
                .build()
    }

    override fun onLoad(savedInstanceState: Bundle?) {
        super.onLoad(savedInstanceState)
        view?.setPhoto(photoUri)
        model.getAlbumList()
                .subscribe({ view?.initListAlbum(it, albumIdSelected) }, { getRootView()?.showError(it) })
    }

    fun onClickItem(albumId: String) {
        if (albumIdSelected == albumId) {
            albumIdSelected = null
        } else {
            albumIdSelected = albumId
        }
        view.selectAlbum(albumIdSelected)
    }
}
