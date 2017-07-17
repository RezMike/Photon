package io.github.rezmike.photon.ui.dialogs

import android.os.Parcelable
import io.github.rezmike.photon.data.storage.dto.DialogResult
import mortar.PopupPresenter

abstract class AbstractDialogPresenter<D : Parcelable, out V : AbstractDialog<D>> : PopupPresenter<D, DialogResult>() {

    var onResult: (DialogResult) -> Unit = {}

    fun setOnResultListener(listener: (DialogResult) -> Unit) {
        onResult = listener
    }

    abstract fun show()

    abstract fun onClickOk()

    fun onClickCancel() {
        onPopupResult(DialogResult(false))
    }

    override fun onPopupResult(result: DialogResult) {
        getDialog()?.dismiss()
        onResult(result)
    }

    @Suppress("UNCHECKED_CAST")
    fun getDialog(): V? = view as V
}