package io.github.rezmike.photon.ui.screens.album

import android.os.Bundle
import android.view.MenuItem
import flow.Flow
import io.github.rezmike.photon.R
import io.github.rezmike.photon.data.storage.realm.AlbumRealm
import io.github.rezmike.photon.data.storage.realm.PhotoCardRealm
import io.github.rezmike.photon.ui.activities.root.BottomBarItems
import io.github.rezmike.photon.ui.others.MenuItemHolder
import io.github.rezmike.photon.ui.screens.AbstractPresenter
import io.github.rezmike.photon.ui.screens.photocard.PhotocardScreen
import io.github.rezmike.photon.ui.screens.upload.UploadScreen
import io.github.rezmike.photon.utils.DaggerService
import io.realm.RealmChangeListener
import mortar.MortarScope
import rx.android.schedulers.AndroidSchedulers

class AlbumPresenter(val albumRealm: AlbumRealm, val bottomBarItem: BottomBarItems) : AbstractPresenter<AlbumView, AlbumModel, AlbumPresenter>() {

    private var listener: RealmChangeListener<AlbumRealm>? = null

    override fun initDagger(scope: MortarScope) {
        DaggerService.getDaggerComponent<AlbumScreen.Component>(scope).inject(this)
    }

    override fun initActionBar() {
        val actionBar = rootPresenter.ActionBarBuilder()
                .setBackArrow(true)
                .setTitle(view?.context?.getString(R.string.album_title)!!)
        if (albumRealm.owner == model.getUserId()) {
            actionBar.setOverFlowIcon(R.drawable.ic_custom_menu_black_24dp)
            actionBar.addAction(MenuItemHolder(view?.context?.getString(R.string.album_menu_add_photocard), 0, { onClickAddPhotoCard() }, MenuItem.SHOW_AS_ACTION_NEVER))
            if (!albumRealm.isFavorite) {
                actionBar.addAction(MenuItemHolder(view?.context?.getString(R.string.album_menu_edit), 0, { onClickEditAlbum() }, MenuItem.SHOW_AS_ACTION_NEVER))
            }
            actionBar.addAction(MenuItemHolder(view?.context?.getString(R.string.album_menu_delete), 0, { onClickDeleteAlbum() }, MenuItem.SHOW_AS_ACTION_NEVER))
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
            view?.showEmptyList()
        } else {
            view?.showList(albumRealm)
        }
    }

    override fun dropView(view: AlbumView) {
        albumRealm.removeChangeListener(listener)
        super.dropView(view)
    }

    fun onClickAddPhotoCard(): Boolean {
        Flow.get(view).set(UploadScreen(albumRealm.id))
        return true
    }

    fun onClickEditAlbum(): Boolean {
        rootPresenter.showAlbumDialog(album = albumRealm)
        return true
    }

    fun onClickDeleteAlbum(): Boolean {
        view.showDeleteDialog()
        return true
    }

    fun onClickItem(photo: PhotoCardRealm) {
        Flow.get(view).set(PhotocardScreen(photo, bottomBarItem))
    }

    fun deleteAlbum() {
        getRootView()?.showLoad()
        model.deleteAlbum(albumRealm.id)
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate { getRootView()?.hideLoad() }
                .subscribe({ getRootView()?.onBackPressed() }, { getRootView()?.showError(it) })
    }
}