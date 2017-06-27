package io.github.rezmike.foton.ui.screens.upload

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import io.github.rezmike.foton.data.storage.dto.ActivityResultDto
import io.github.rezmike.foton.ui.abstracts.AbstractPresenter
import io.github.rezmike.foton.utils.ConstantManager.REQUEST_PERMISSION_READ_EXTERNAL_STORAGE
import io.github.rezmike.foton.utils.ConstantManager.REQUEST_PHOTOCARD_PHOTO_GALLERY
import io.github.rezmike.foton.utils.DaggerService
import mortar.MortarScope
import rx.Subscription

class UploadPresenter : AbstractPresenter<UploadView, UploadModel, UploadPresenter>() {

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
        val intent = Intent()
        if (Build.VERSION.SDK_INT < 19) {
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
        } else {
            intent.type = "image/*"
            intent.action = Intent.ACTION_OPEN_DOCUMENT
            intent.addCategory(Intent.CATEGORY_OPENABLE)
        }
        getRootView()?.startActivityForResult(intent, REQUEST_PHOTOCARD_PHOTO_GALLERY)
    }

    private fun subscribeOnActivityResult(): Subscription {
        return rootPresenter.getActivityResultSubject()
                .subscribe(object : ViewSubscriber<ActivityResultDto>() {
                    override fun onNext(activityResult: ActivityResultDto?) {
                        if (activityResult != null) handleActivityResult(activityResult);
                    }
                })
    }

    private fun handleActivityResult(activityResult: ActivityResultDto) {
        if (activityResult.resultCode == Activity.RESULT_OK &&
                activityResult.requestCode == REQUEST_PHOTOCARD_PHOTO_GALLERY &&
                activityResult.intent != null) {
            Log.d("UploadPresenter", "handle");
            val photoUri = activityResult.intent.data.toString()
            view?.showAddInfoScreen(photoUri)
        }
    }
}
