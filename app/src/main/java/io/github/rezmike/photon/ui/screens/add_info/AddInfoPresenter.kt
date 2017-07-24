package io.github.rezmike.photon.ui.screens.add_info

import android.os.Bundle
import io.github.rezmike.photon.R
import io.github.rezmike.photon.ui.screens.AbstractPresenter
import io.github.rezmike.photon.utils.DaggerService
import mortar.MortarScope

class AddInfoPresenter(val photoUri: String, val albumId: String?) : AbstractPresenter<AddInfoView, AddInfoModel, AddInfoPresenter>() {

    private var albumIdSelected: String = ""

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
        model.getAlbums()
                .subscribe({ view?.initListAlbum(it) }, { getRootView()?.showError(it) })
    }

    fun onClickItem(albumId: String) {
        if(albumIdSelected == albumId){
            albumIdSelected = ""
        }else{
            albumIdSelected = albumId
        }
        view.selectAlbum(albumIdSelected)
    }
}
