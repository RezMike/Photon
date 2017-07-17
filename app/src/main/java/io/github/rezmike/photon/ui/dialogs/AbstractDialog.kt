package io.github.rezmike.photon.ui.dialogs

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import io.github.rezmike.photon.R
import io.github.rezmike.photon.data.storage.dto.DialogResult
import mortar.Popup
import mortar.PopupPresenter

abstract class AbstractDialog<D : Parcelable>(context: Context) : Popup<D, DialogResult> {

    private val rootContext = context

    private var dialog: AlertDialog? = null

    override fun show(info: D, withFlourish: Boolean, presenter: PopupPresenter<D, DialogResult>) {
        val view = LayoutInflater.from(rootContext).inflate(getLayoutRes(), null, false)
        onFinishInflate(view, presenter, info)
        dialog = AlertDialog.Builder(rootContext)
                .setView(view)
                .setOnCancelListener { presenter.onDismissed(DialogResult(false)) }
                .create()
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.show()
    }

    fun dismiss() {
        onDialogDismiss()
        dialog?.dismiss()
        dialog = null
    }

    abstract fun getLayoutRes(): Int

    abstract fun onFinishInflate(view: View, presenter: PopupPresenter<D, DialogResult>, info: D)

    abstract fun onDialogDismiss()

    override fun isShowing() = dialog != null
    override fun getContext() = rootContext
    override fun dismiss(withFlourish: Boolean) = dismiss()

    //region ======================== Events ========================

    fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun showMessage(stringResId: Int) {
        showMessage(context.getString(stringResId))
    }

    fun showError(error: Throwable) {
        showMessage(error.message ?: context.getString(R.string.error_unknown))
    }

    //endregion
}