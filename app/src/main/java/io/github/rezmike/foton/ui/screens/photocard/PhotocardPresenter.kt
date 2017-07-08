package io.github.rezmike.foton.ui.screens.photocard

import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.os.Bundle
import android.view.MenuItem
import flow.Flow
import io.github.rezmike.foton.R
import io.github.rezmike.foton.data.storage.PhotoCardRealm
import io.github.rezmike.foton.ui.abstracts.AbstractPresenter
import io.github.rezmike.foton.ui.activities.root.MenuItemHolder
import io.github.rezmike.foton.ui.screens.user.UserScreen
import io.github.rezmike.foton.utils.ConstantManager
import io.github.rezmike.foton.utils.DaggerService
import mortar.MortarScope
import rx.android.schedulers.AndroidSchedulers

class PhotocardPresenter(val photoCard: PhotoCardRealm) : AbstractPresenter<PhotocardView, PhotocardModel, PhotocardPresenter>() {

    override fun initDagger(scope: MortarScope) {
        DaggerService.getDaggerComponent<PhotocardScreen.Component>(scope).inject(this)
    }

    override fun initActionBar() {
        rootPresenter.ActionBarBuilder()
                .setTitle(view?.resources?.getString(R.string.photocard_title)!!)
                .setBackArrow(true)
                .setOverFlowIcon(R.drawable.ic_custom_menu_black_24dp)
                .addAction(MenuItemHolder(view?.context?.getString(R.string.photocard_menu_in_favorites), 0, { onClickFavorite() }, MenuItem.SHOW_AS_ACTION_NEVER))
                .addAction(MenuItemHolder(view?.context?.getString(R.string.photocard_menu_share), 0, { onClickShare() }, MenuItem.SHOW_AS_ACTION_NEVER))
                .addAction(MenuItemHolder(view?.context?.getString(R.string.photocard_menu_save), 0, { onClickSave() }, MenuItem.SHOW_AS_ACTION_NEVER))
                .build()
    }

    override fun onLoad(savedInstanceState: Bundle?) {
        super.onLoad(savedInstanceState)

        loadPhotocardInfo()
    }

    private fun loadPhotocardInfo() {
        getRootView()?.showProgress()
        model.getUserData(photoCard.owner)
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate { getRootView()?.hideProgress() }
                .subscribe({ view?.showPhotoCardInfo(photoCard, it) }, { getRootView()?.showError(it) })
    }

    fun onClickFavorite(): Boolean {
        if (rootPresenter.isUserAuth()) {
            model.saveFavorite(photoCard.id)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({}, { getRootView()?.showError(it) })
        } else {
            getRootView()?.showMessage(R.string.error_need_enter_account)
        }
        return true
    }

    fun onClickShare(): Boolean {
        rootPresenter.sharePhoto(photoCard.photo)
        return true
    }

    fun onClickSave(): Boolean {
        val permissions = arrayOf(WRITE_EXTERNAL_STORAGE)
        if (rootPresenter.checkPermissionAndRequestIfNotGranted(permissions, ConstantManager.REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE)) {
            model.downloadAndSavePhotoFile(photoCard.photo)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ getRootView()?.showMessage(R.string.photocard_saving_completed) }, { getRootView()?.showError(it) })
        }
        return true
    }

    fun onClickUserInfo(userId: String) {
        Flow.get(view).set(UserScreen(userId))
    }
}
