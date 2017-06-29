package io.github.rezmike.foton.ui.screens.photocard

import android.Manifest
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.os.Bundle
import android.view.MenuItem
import io.github.rezmike.foton.R
import io.github.rezmike.foton.data.storage.PhotoCardRealm
import io.github.rezmike.foton.ui.abstracts.AbstractPresenter
import io.github.rezmike.foton.ui.activities.root.MenuItemHolder
import io.github.rezmike.foton.utils.DaggerService
import mortar.MortarScope
import rx.android.schedulers.AndroidSchedulers
import android.provider.MediaStore
import android.content.ContentValues
import android.content.Context
import android.os.Environment.DIRECTORY_PICTURES
import android.os.Environment.getExternalStoragePublicDirectory
import io.github.rezmike.foton.utils.ConstantManager
import io.github.rezmike.foton.ui.activities.root.RootActivity
import android.content.Intent
import android.os.Environment
import io.github.rezmike.foton.utils.createFileFromPhoto
import java.io.File
import java.io.IOException
import java.text.DateFormat
import java.util.*


class PhotocardPresenter(val photoCard: PhotoCardRealm) : AbstractPresenter<PhotocardView, PhotocardModel, PhotocardPresenter>() {

    override fun initDagger(scope: MortarScope) {
        DaggerService.getDaggerComponent<PhotocardScreen.Component>(scope).inject(this)
    }

    override fun initActionBar() {
        rootPresenter.ActionBarBuilder()
                .setTitle(view?.resources?.getString(R.string.photocard_title)!!)
                .setBackArrow(true)
                .setOverFlowIcon(R.drawable.ic_custom_menu_black_24dp)
                .addAction(MenuItemHolder(view?.context?.getString(R.string.photocard_menu_in_favorites), 0, { onClickInFavorite() }, MenuItem.SHOW_AS_ACTION_NEVER))
                .addAction(MenuItemHolder(view?.context?.getString(R.string.photocard_menu_share), 0, { onClickShare() }, MenuItem.SHOW_AS_ACTION_NEVER))
                .addAction(MenuItemHolder(view?.context?.getString(R.string.photocard_menu_save), 0, { onClickSave() }, MenuItem.SHOW_AS_ACTION_NEVER))
                .build()
    }

    override fun onLoad(savedInstanceState: Bundle?) {
        super.onLoad(savedInstanceState)
        model.getAuthorPhoto(photoCard.owner)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({view.showPhotoCardInfo(photoCard, it)},{e: Throwable -> getRootView()?.showError(e) })

    }

    private fun onClickInFavorite(): Boolean {
        return true
    }

    private fun onClickShare(): Boolean {


        return true
    }

    private fun onClickSave(): Boolean {
        val permissions = arrayOf(WRITE_EXTERNAL_STORAGE)
        if(rootPresenter.checkPermissionAndRequestIfNotGranted(permissions, ConstantManager.REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE)){
//            val photoFile = view.context.createFileFromPhoto()
        }

        return true
    }


}
