package io.github.rezmike.photon.ui.dialogs

import android.os.Parcelable
import io.github.rezmike.photon.data.storage.dto.DialogResult
import mortar.PopupPresenter

abstract class AbstractDialogPresenter<D : Parcelable, out V : AbstractDialog<D>> : PopupPresenter<D, DialogResult>() {

    private var onResult: (DialogResult) -> Unit = {}

    fun setOnResultListener(listener: (DialogResult) -> Unit) {
        onResult = listener
    }

    abstract fun show()

    abstract fun onClickOk()

    fun onClickCancel() {
        onDialogResult(false)
    }

    fun onDialogResult(result: Boolean) {
        onDismissed(DialogResult(result))
    }

    override fun onPopupResult(result: DialogResult) {
        dropView(getDialog())
        onResult(result)
    }

    @Suppress("UNCHECKED_CAST")
    fun getDialog() = view as V?
}