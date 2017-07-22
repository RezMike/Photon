package io.github.rezmike.photon.ui.screens.album

import android.Manifest
import android.app.Activity
import android.os.Bundle
import android.view.MenuItem
import flow.Flow
import io.github.rezmike.photon.R
import io.github.rezmike.photon.data.storage.dto.ActivityResultDto
import io.github.rezmike.photon.data.storage.realm.AlbumRealm
import io.github.rezmike.photon.data.storage.realm.PhotoCardRealm
import io.github.rezmike.photon.ui.activities.root.BottomBarItems
import io.github.rezmike.photon.ui.others.MenuItemHolder
import io.github.rezmike.photon.ui.screens.AbstractPresenter
import io.github.rezmike.photon.ui.screens.photocard.PhotocardScreen
import io.github.rezmike.photon.utils.ActionHelper
import io.github.rezmike.photon.utils.ConstantManager
import io.github.rezmike.photon.utils.DaggerService
import io.realm.RealmChangeListener
import mortar.MortarScope
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers

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
        compSubs.add(subscribeOnActivityResult())
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
        val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
        if (rootPresenter.checkPermissionAndRequestIfNotGranted(permissions, ConstantManager.REQUEST_PERMISSION_READ_EXTERNAL_STORAGE)) {
            takePhotoFromGallery()
        }
        return true
    }

    private fun onClickDeleteAlbum(): Boolean {
        view?.showDeleteDialog()
        return true
    }

    private fun onClickEditAlbum(): Boolean {
        rootPresenter.showAlbumDialog({}, albumRealm)
        return true
    }

    fun deleteAlbum() {
        getRootView()?.showLoad()
        model.deleteAlbum(albumRealm.id)
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate { getRootView()?.hideLoad() }
                .subscribe({
                    getRootView()?.onBackPressed()
                }, { getRootView()?.showError(it) })
    }

    fun onClickOnPhoto(photo: PhotoCardRealm) {
        Flow.get(view).set(PhotocardScreen(photo, BottomBarItems.PROFILE))
    }

    private fun takePhotoFromGallery() {
        getRootView()?.startActivityForResult(ActionHelper.getGalleryIntent(), ConstantManager.REQUEST_PHOTOCARD_PHOTO_GALLERY)
    }

    private fun subscribeOnActivityResult(): Subscription {
        return rootPresenter.getActivityResultSubject()
                .subscribe({ if (it != null) handleActivityResult(it) }, { getRootView()?.showError(it) })
    }

    private fun handleActivityResult(activityResult: ActivityResultDto) {
        if (activityResult.resultCode == Activity.RESULT_OK &&
                activityResult.requestCode == ConstantManager.REQUEST_PHOTOCARD_PHOTO_GALLERY &&
                activityResult.intent != null) {
            val photoUri = activityResult.intent.data.toString()
            view?.showAddInfoScreen(photoUri)
        }
    }
}