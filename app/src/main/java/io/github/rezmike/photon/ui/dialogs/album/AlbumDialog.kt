package io.github.rezmike.photon.ui.dialogs.album

import android.content.Context
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import io.github.rezmike.photon.R
import io.github.rezmike.photon.data.storage.dto.AlbumInfoDto
import io.github.rezmike.photon.data.storage.dto.DialogResult
import io.github.rezmike.photon.ui.dialogs.AbstractDialog
import io.github.rezmike.photon.ui.others.accentAnim
import io.github.rezmike.photon.ui.others.addAfterTextChangedListener
import io.github.rezmike.photon.ui.others.changeError
import mortar.PopupPresenter

class AlbumDialog(context: Context, val isEditMode: Boolean) : AbstractDialog<AlbumInfoDto>(context) {

    private var header: TextView? = null
    private var title: EditText? = null
    private var description: EditText? = null
    private var okBtn: Button? = null
    private var cancelBtn: Button? = null

    override fun getLayoutRes(): Int = R.layout.dialog_album

    override fun onFinishInflate(view: View, presenter: PopupPresenter<AlbumInfoDto, DialogResult>, info: AlbumInfoDto) {
        if (presenter !is AlbumDialogPresenter) {
            throw ClassCastException("Presenter must be AlbumDialogPresenter")
        }

        if (isEditMode) {
            header = view.findViewById(R.id.dialog_title_tv) as TextView
            header?.text = view.resources.getString(R.string.album_dialog_edit_header)
        }

        title = view.findViewById(R.id.title_et) as EditText
        description = view.findViewById(R.id.description_et) as EditText
        okBtn = view.findViewById(R.id.ok_btn) as Button
        cancelBtn = view.findViewById(R.id.cancel_btn) as Button

        title?.addAfterTextChangedListener { presenter.checkName(it) }
        description?.addAfterTextChangedListener { presenter.checkDescription(it) }

        title?.setText(info.title)
        description?.setText(info.description)

        okBtn?.setOnClickListener { presenter.onClickOk() }
        cancelBtn?.setOnClickListener { presenter.onClickCancel() }
    }

    override fun onDialogDismiss() {
        title = null
        description = null
        okBtn = null
        cancelBtn = null
    }

    //region ======================== Error ========================

    fun showTitleError() {
        title?.changeError(true, context.getString(R.string.album_dialog_title_error))
    }

    fun hideTitleError() {
        title?.changeError(false)
    }

    fun showDescriptionError() {
        description?.changeError(true, context.getString(R.string.album_dialog_title_error))
    }

    fun hideDescriptionError() {
        description?.changeError(false)
    }

    //endregion

    //region ======================== Anim ========================

    fun accentTitle() {
        accentAnim(title!!)
    }

    fun accentDescription() {
        accentAnim(description!!)
    }

    //endregion
}