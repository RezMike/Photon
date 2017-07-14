package io.github.rezmike.photon.ui.dialogs.album

import io.github.rezmike.photon.R
import io.github.rezmike.photon.data.storage.dto.AlbumInfoDto
import io.github.rezmike.photon.data.storage.dto.DialogResult
import io.github.rezmike.photon.ui.activities.root.AccountModel
import io.github.rezmike.photon.ui.dialogs.AbstractDialogPresenter
import io.github.rezmike.photon.ui.others.isAlbumDescriptionValid
import io.github.rezmike.photon.ui.others.isAlbumTitleValid
import rx.android.schedulers.AndroidSchedulers

class AlbumDialogPresenter(val model: AccountModel) : AbstractDialogPresenter<AlbumInfoDto, AlbumDialog>() {

    private var title: String = ""
    private var description: String = ""

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
            model.createAlbum(title, description)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        getDialog()?.showMessage(R.string.album_dialog_success)
                        getDialog()?.dismiss()
                        onResult(DialogResult(true))
                    }, {
                        getDialog()?.showError(it)
                    })
        } else {
            if (!title.isAlbumTitleValid()) getDialog()?.accentTitle()
            if (!description.isAlbumDescriptionValid()) getDialog()?.accentDescription()
        }
    }
}