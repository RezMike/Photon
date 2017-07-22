package io.github.rezmike.photon.ui.dialogs.album

import android.text.TextUtils
import io.github.rezmike.photon.R
import io.github.rezmike.photon.data.storage.dto.AlbumInfoDto
import io.github.rezmike.photon.data.storage.dto.DialogResult
import io.github.rezmike.photon.data.storage.realm.AlbumRealm
import io.github.rezmike.photon.ui.activities.root.AccountModel
import io.github.rezmike.photon.ui.dialogs.AbstractDialogPresenter
import io.github.rezmike.photon.ui.others.isAlbumDescriptionValid
import io.github.rezmike.photon.ui.others.isAlbumTitleValid

class AlbumDialogPresenter(val model: AccountModel, val album: AlbumRealm?) : AbstractDialogPresenter<AlbumInfoDto, AlbumDialog>() {

    private var title: String = ""
    private var description: String = ""

    override fun show() {
        if (isEdit()) {
            title = album!!.title
            description = album.description
        }
        show(AlbumInfoDto(title, description))
    }

    fun checkName(title: String) {
        this.title = title.toUpperCaseFirstChar()
        if (title.isAlbumTitleValid()) {
            getDialog()?.hideTitleError()
        } else {
            getDialog()?.showTitleError()
        }
    }

    fun checkDescription(description: String) {
        this.description = description.toUpperCaseFirstChar()
        if (description.isAlbumDescriptionValid()) {
            getDialog()?.hideDescriptionError()
        } else {
            getDialog()?.showDescriptionError()
        }
    }

    override fun onClickOk() {
        if (title.isEmpty() || description.isEmpty()) {
            if (title.isEmpty()) getDialog()?.accentTitle()
            if (description.isEmpty()) getDialog()?.accentDescription()
            getDialog()?.showMessage(R.string.album_dialog_error_empty_fields)
        } else if (title.isAlbumTitleValid() && description.isAlbumDescriptionValid()) {
            if (isEdit()) {
                if (title == album!!.title && description == album.description) onClickCancel()
                else model.editAlbum(album.id, title, description)
            } else {
                model.createAlbum(title, description)
                getDialog()?.showMessage(R.string.album_dialog_success)
            }
            getDialog()?.dismiss()
            onResult(DialogResult(true))
        } else {
            if (!title.isAlbumTitleValid()) getDialog()?.accentTitle()
            if (!description.isAlbumDescriptionValid()) getDialog()?.accentDescription()
        }
    }

    private fun String.toUpperCaseFirstChar(): String {
        if (TextUtils.isEmpty(this)) return this
        if (this[0].isUpperCase()) return this
        val builder = StringBuilder(this)
        builder.setCharAt(0, Character.toUpperCase(this[0]))
        return builder.toString()
    }

    fun isEdit() = album != null
}