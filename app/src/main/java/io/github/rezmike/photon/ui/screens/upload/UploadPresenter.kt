package io.github.rezmike.photon.ui.screens.upload

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.app.Activity
import android.os.Bundle
import io.github.rezmike.photon.data.storage.dto.ActivityResultDto
import io.github.rezmike.photon.ui.activities.root.AccountModel
import io.github.rezmike.photon.ui.screens.AbstractPresenter
import io.github.rezmike.photon.utils.ConstantManager.REQUEST_PERMISSION_READ_EXTERNAL_STORAGE
import io.github.rezmike.photon.utils.ConstantManager.REQUEST_PHOTOCARD_PHOTO_GALLERY
import io.github.rezmike.photon.utils.DaggerService
import io.github.rezmike.photon.utils.getGalleryIntent
import mortar.MortarScope
import rx.Subscription

class UploadPresenter : AbstractPresenter<UploadView, AccountModel, UploadPresenter>() {

    override fun initDagger(scope: MortarScope) {
        DaggerService.getDaggerComponent<UploadScreen.Component>(scope).inject(this)
    }

    override fun initActionBar() {
        rootPresenter.ActionBarBuilder()
                .setVisible(false)
                .build()
    }

    override fun onLoad(savedInstanceState: Bundle?) {
        super.onLoad(savedInstanceState)
        compSubs.add(subscribeOnActivityResult())
    }

    fun clickOnChoosePhoto() {
        val permissions = arrayOf(READ_EXTERNAL_STORAGE)
        if (rootPresenter.checkPermissionAndRequestIfNotGranted(permissions, REQUEST_PERMISSION_READ_EXTERNAL_STORAGE)) {
            takePhotoFromGallery()
        }
    }

    private fun takePhotoFromGallery() {
        getRootView()?.startActivityForResult(getGalleryIntent(), REQUEST_PHOTOCARD_PHOTO_GALLERY)
    }

    private fun subscribeOnActivityResult(): Subscription {
        return rootPresenter.getActivityResultSubject()
                .subscribe({ if (it != null) handleActivityResult(it) }, { getRootView()?.showError(it) })
    }

    private fun handleActivityResult(activityResult: ActivityResultDto) {
        if (activityResult.resultCode == Activity.RESULT_OK &&
                activityResult.requestCode == REQUEST_PHOTOCARD_PHOTO_GALLERY &&
                activityResult.intent != null) {
            val photoUri = activityResult.intent.data.toString()
            view?.showAddInfoScreen(photoUri)
        }
    }
}
