package io.github.rezmike.photon.ui.dialogs.album

import io.github.rezmike.photon.R
import io.github.rezmike.photon.data.storage.dto.AlbumInfoDto
import io.github.rezmike.photon.data.storage.dto.DialogResult
import io.github.rezmike.photon.data.storage.realm.AlbumRealm
import io.github.rezmike.photon.ui.activities.root.AccountModel
import io.github.rezmike.photon.ui.dialogs.AbstractDialogPresenter
import io.github.rezmike.photon.ui.others.isAlbumDescriptionValid
import io.github.rezmike.photon.ui.others.isAlbumTitleValid

class AlbumDialogPresenter(val model: AccountModel, val album: AlbumRealm?) : AbstractDialogPresenter<AlbumInfoDto, AlbumDialog>() {

    private var title: String = album?.title ?: ""
    private var description: String = album?.description ?: ""

    override fun show() = show(AlbumInfoDto(title, description))

    fun checkName(title: String) {
        this.title = title
        if (title.isAlbumTitleValid()) {
            getDialog()?.hideTitleError()
        } else {
            getDialog()?.showTitleError()
        }
    }

    fun checkDescription(description: String) {
        this.description = description
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
            if (isEditMode()) {
                if (title == album!!.title && description == album.description) onClickCancel() // TODO: 22.07.2017 change to onDialogResult(false)
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

    fun isEditMode() = album != null
}