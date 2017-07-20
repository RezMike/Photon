package io.github.rezmike.photon.ui.dialogs.avatar

import android.Manifest.permission.*
import android.app.Activity
import io.github.rezmike.photon.utils.ConstantManager.REQUEST_PERMISSION_READ_EXTERNAL_STORAGE
import io.github.rezmike.photon.utils.ConstantManager.REQUEST_PHOTOCARD_PHOTO_GALLERY
import android.net.Uri
import android.os.Bundle
import android.util.Log
import io.github.rezmike.photon.R
import io.github.rezmike.photon.data.storage.dto.ActivityResultDto
import io.github.rezmike.photon.data.storage.dto.DialogResult
import io.github.rezmike.photon.ui.activities.root.AccountModel
import io.github.rezmike.photon.ui.activities.root.RootActivity
import io.github.rezmike.photon.ui.activities.root.RootPresenter
import io.github.rezmike.photon.ui.dialogs.AbstractDialogPresenter
import io.github.rezmike.photon.utils.ActionHelper
import io.github.rezmike.photon.utils.ConstantManager
import io.github.rezmike.photon.utils.ConstantManager.REQUEST_CAMERA_PICTURE
import io.github.rezmike.photon.utils.ConstantManager.REQUEST_PERMISSION_CAMERA
import io.github.rezmike.photon.utils.createFileFromPhoto
import mortar.Popup
import rx.Subscription
import java.io.File

class AvatarDialogPresenter(val model: AccountModel, val rootPresenter: RootPresenter) : AbstractDialogPresenter<Uri, AvatarDialog>() {

    private var photoUri: Uri = Uri.EMPTY
    val photoFile: File? = null
    private var requestSub: Subscription? = null

    override fun onLoad(savedInstanceState: Bundle?) {
        super.onLoad(savedInstanceState)
        requestSub = subscribeOnActivityResult()
    }

    override fun dropView(view: Popup<Uri, DialogResult>?) {
        requestSub?.unsubscribe()
        super.dropView(view)
    }

    override fun show() = show(photoUri)

    fun onClickCamera() {
        val permission = arrayOf(CAMERA, WRITE_EXTERNAL_STORAGE)
        if (rootPresenter.checkPermissionAndRequestIfNotGranted(permission, REQUEST_PERMISSION_CAMERA)) {

            val photoFile = createFileFromPhoto()
            if (photoFile == null) {
                getDialog()?.showMessage(R.string.avatar_dialog_file_not_created)
                return
            }
            rootPresenter.getRootView()?.startActivityForResult(ActionHelper.getCameraIntent(photoFile), REQUEST_PHOTOCARD_PHOTO_GALLERY)
        }
    }

    fun onClickGallery() {
        val permission = arrayOf(READ_EXTERNAL_STORAGE)
        if (rootPresenter.checkPermissionAndRequestIfNotGranted(permission, REQUEST_PERMISSION_READ_EXTERNAL_STORAGE)) {
            rootPresenter.getRootView()?.startActivityForResult(ActionHelper.getGalleryIntent(), REQUEST_PHOTOCARD_PHOTO_GALLERY)
        }
    }

    override fun onClickOk() {
        dismiss()
    }


    private fun subscribeOnActivityResult(): Subscription {
        return rootPresenter.getActivityResultSubject()
                .subscribe({ if (it != null) handleActivityResult(it) }, { getRootView()?.showError(it) })
    }


    private fun handleActivityResult(activityResult: ActivityResultDto) {
        if (activityResult.resultCode == Activity.RESULT_OK) {
            when (activityResult.requestCode) {
                REQUEST_PHOTOCARD_PHOTO_GALLERY -> {
                    if (activityResult.intent != null)
                        photoUri = activityResult.intent.data

                }
                REQUEST_CAMERA_PICTURE -> {
                    if (photoFile != null) photoUri = Uri.fromFile(photoFile)
                }
            }
            Log.e("sub", photoUri.toString())
            model.uploadAvatarToServer(photoUri.toString())
            dismiss()
        } else {
            when (activityResult.requestCode) {
                REQUEST_CAMERA_PICTURE -> if (photoFile != null) getRootView()?.deleteFile(photoFile.name)
            }
        }
    }

    private fun getRootView(): RootActivity? {
        return rootPresenter.getRootView()
    }
}