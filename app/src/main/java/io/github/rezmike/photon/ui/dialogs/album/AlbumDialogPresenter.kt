package io.github.rezmike.photon.ui.dialogs.album

import io.github.rezmike.photon.R
import io.github.rezmike.photon.data.storage.dto.AlbumInfoDto
import io.github.rezmike.photon.data.storage.dto.DialogResult
import io.github.rezmike.photon.ui.activities.root.AccountModel
import io.github.rezmike.photon.ui.dialogs.AbstractDialogPresenter
import io.github.rezmike.photon.ui.others.isNameValid

class AlbumDialogPresenter(val model: AccountModel) : AbstractDialogPresenter<AlbumInfoDto, AlbumDialog>() {

    private var name: String = ""
    private var description: String = ""

    override fun show() = show(AlbumInfoDto(name, description))

    fun checkName(name: String) {
        this.name = name
        if (name.isNameValid()) {
            getDialog()?.hideNameError()
        } else {
            getDialog()?.showNameError()
        }
    }

    fun checkDescription(description: String) {
        this.description = description
        if (description.isNameValid()) {
            getDialog()?.hideDescriptionError()
        } else {
            getDialog()?.showDescriptionError()
        }
    }

    override fun onClickOk() {
        if (name.isEmpty() || description.isEmpty()) {
            if (name.isEmpty()) getDialog()?.accentTitle()
            if (description.isEmpty()) getDialog()?.accentDescription()
            getDialog()?.showMessage(R.string.album_error_empty_fields)
        } else if (name.isNameValid() && description.isNameValid()) {
            getDialog()?.dismiss()
            onResult(DialogResult(true))
        }
    }
}