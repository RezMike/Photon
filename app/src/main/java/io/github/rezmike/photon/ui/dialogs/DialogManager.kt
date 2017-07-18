package io.github.rezmike.photon.ui.dialogs

import android.content.Context
import io.github.rezmike.photon.data.storage.dto.DialogResult
import io.github.rezmike.photon.ui.activities.root.AccountModel
import io.github.rezmike.photon.ui.dialogs.album.AlbumDialog
import io.github.rezmike.photon.ui.dialogs.album.AlbumDialogPresenter
import io.github.rezmike.photon.ui.dialogs.login.LoginDialog
import io.github.rezmike.photon.ui.dialogs.login.LoginDialogPresenter

class DialogManager(val model: AccountModel) {

    private var loginDialogPresenter: LoginDialogPresenter? = null
    private var loginDialog: LoginDialog? = null

    private var albumDialogPresenter: AlbumDialogPresenter? = null
    private var albumDialog: AlbumDialog? = null

    fun showLoginDialog(context: Context, onResult: (DialogResult) -> Unit = {}) {
        loginDialogPresenter = LoginDialogPresenter(model)
        loginDialogPresenter?.setOnResultListener {
            loginDialogPresenter = null
            loginDialog = null
            onResult(it)
        }
        loginDialog = LoginDialog(context)
        loginDialogPresenter?.takeView(loginDialog)
        loginDialogPresenter?.show()
    }

    fun showAlbumDialog(context: Context, onResult: (DialogResult) -> Unit = {}) {
        albumDialogPresenter = AlbumDialogPresenter(model)
        albumDialogPresenter?.setOnResultListener {
            albumDialogPresenter = null
            albumDialog = null
            onResult(it)
        }
        albumDialog = AlbumDialog(context)
        albumDialogPresenter?.takeView(albumDialog)
        albumDialogPresenter?.show()
    }

    fun dismissDialogs() {
        if (loginDialogPresenter != null) {
            loginDialogPresenter?.dismiss()
        }
        loginDialog = null
        if (albumDialogPresenter != null) {
            albumDialogPresenter?.dismiss()
        }
        albumDialog = null
    }

    fun showHiddenDialogs(context: Context) {
        if (loginDialogPresenter != null) {
            loginDialog = LoginDialog(context)
            loginDialogPresenter?.takeView(loginDialog)
            loginDialogPresenter?.show()
        }
        if (albumDialogPresenter != null) {
            albumDialog = AlbumDialog(context)
            albumDialogPresenter?.takeView(albumDialog)
            albumDialogPresenter?.show()
        }
    }
}
