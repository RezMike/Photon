package io.github.rezmike.photon.ui.dialogs.avatar

import android.content.Context
import android.net.Uri
import android.view.View
import android.widget.Button
import io.github.rezmike.photon.R
import io.github.rezmike.photon.data.storage.dto.DialogResult
import io.github.rezmike.photon.ui.dialogs.AbstractDialog
import mortar.PopupPresenter


class AvatarDialog(context: Context) : AbstractDialog<Uri>(context) {

    private var okBtn: Button? = null
    private var cancelBtn: Button? = null
    private var cameraBtn: Button? = null
    private var galleryBtn: Button? = null

    override fun getLayoutRes() = R.layout.dialog_change_avatar

    override fun onFinishInflate(view: View, presenter: PopupPresenter<Uri, DialogResult>, info: Uri) {
        if (presenter !is AvatarDialogPresenter) {
            throw ClassCastException("Presenter must be AvatarDialogPresenter")
        }

        okBtn = view.findViewById(R.id.ok_btn) as Button
        cancelBtn = view.findViewById(R.id.cancel_btn) as Button
        cameraBtn = view.findViewById(R.id.camera_btn) as Button
        galleryBtn = view.findViewById(R.id.gallery_btn) as Button

        okBtn?.setOnClickListener { presenter.onClickOk() }
        cancelBtn?.setOnClickListener { presenter.onClickCancel() }
        cameraBtn?.setOnClickListener { presenter.onClickCamera() }
        galleryBtn?.setOnClickListener { presenter.onClickGallery() }
    }

    override fun onDialogDismiss() {
        okBtn = null
        cancelBtn = null
        cameraBtn = null
        galleryBtn = null
    }
}