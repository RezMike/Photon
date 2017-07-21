package io.github.rezmike.photon.ui.screens.album

import android.os.Bundle
import android.view.MenuItem
import io.github.rezmike.photon.R
import io.github.rezmike.photon.data.storage.realm.AlbumRealm
import io.github.rezmike.photon.ui.others.MenuItemHolder
import io.github.rezmike.photon.ui.screens.AbstractPresenter
import io.github.rezmike.photon.utils.DaggerService
import io.realm.RealmChangeListener
import mortar.MortarScope

class AlbumPresenter(val albumRealm: AlbumRealm) : AbstractPresenter<AlbumView, AlbumModel, AlbumPresenter>() {

    private var listener: RealmChangeListener<AlbumRealm>? = null

    override fun initDagger(scope: MortarScope) {
        DaggerService.getDaggerComponent<AlbumScreen.Component>(scope).inject(this)
    }

    override fun initActionBar() {
        val actionBar = rootPresenter.ActionBarBuilder()
                .setBackArrow(true)
                .setTitle(view?.context?.getString(R.string.album_title)!!)
        if (model.getUserId() != null) {
            if (albumRealm.owner == model.getUserId()) {
                actionBar
                        .setOverFlowIcon(R.drawable.ic_custom_menu_black_24dp)
                        .addAction(MenuItemHolder("Добавить фотокарточку", 0, { onClickAddPhotoCard() }, MenuItem.SHOW_AS_ACTION_NEVER))
                        .addAction(MenuItemHolder("Редактировать альбом", 0, { onClickEditAlbum() }, MenuItem.SHOW_AS_ACTION_NEVER))
                        .addAction(MenuItemHolder("Удалить альбом", 0, { onClickDeleteAlbum() }, MenuItem.SHOW_AS_ACTION_NEVER))
            }
        }
        actionBar.build()
    }

    override fun onLoad(savedInstanceState: Bundle?) {
        super.onLoad(savedInstanceState)
        listener = RealmChangeListener {
            initData(it)
        }
        albumRealm.addChangeListener(listener)
        initData(albumRealm)
    }

    private fun initData(albumRealm: AlbumRealm) {
        view?.showAlbumInfo(albumRealm)
        if (albumRealm.photoCards.size == 0) {
            view?.showListEmpty()
        } else {
            view?.showList(albumRealm)
        }

    }

    override fun dropView(view: AlbumView) {
        albumRealm.removeChangeListener(listener)
        super.dropView(view)
    }

    private fun onClickAddPhotoCard(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun onClickDeleteAlbum(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun onClickEditAlbum(): Boolean {
        return true
    }
}