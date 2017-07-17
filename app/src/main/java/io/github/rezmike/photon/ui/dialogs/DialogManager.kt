package io.github.rezmike.photon.ui.dialogs

import android.content.Context
import io.github.rezmike.photon.data.storage.dto.DialogResult
import io.github.rezmike.photon.ui.activities.root.AccountModel
import io.github.rezmike.photon.ui.dialogs.album.AlbumDialog
import io.github.rezmike.photon.ui.dialogs.album.AlbumDialogPresenter
import io.github.rezmike.photon.ui.dialogs.login.LoginDialog
import io.github.rezmike.photon.ui.dialogs.login.LoginDialogPresenter

class DialogManager(val model: AccountModel) {

    private var mLoginDialogDialogPresenter: LoginDialogPresenter? = null
    private var loginDialog: LoginDialog? = null

    private var mAlbumDialogDialogPresenter: AlbumDialogPresenter? = null
    private var albumDialog: AlbumDialog? = null

    fun showLoginDialog(context: Context, onResult: (DialogResult) -> Unit = {}) {
        mLoginDialogDialogPresenter = LoginDialogPresenter(model)
        mLoginDialogDialogPresenter?.setOnResultListener {
            mLoginDialogDialogPresenter = null
            loginDialog = null
            onResult(it)
        }
        loginDialog = LoginDialog(context)
        mLoginDialogDialogPresenter?.takeView(loginDialog)
        mLoginDialogDialogPresenter?.show()
    }

    fun showAlbumDialog(context: Context, onResult: (DialogResult) -> Unit = {}) {
        mAlbumDialogDialogPresenter = AlbumDialogPresenter(model)
        mAlbumDialogDialogPresenter?.setOnResultListener {
            mAlbumDialogDialogPresenter = null
            albumDialog = null
            onResult(it)
        }
        albumDialog = AlbumDialog(context)
        mAlbumDialogDialogPresenter?.takeView(albumDialog)
        mAlbumDialogDialogPresenter?.show()
    }

    fun dismissDialogs() {
        if (mLoginDialogDialogPresenter != null) {
            mLoginDialogDialogPresenter?.dismiss()
        }
        loginDialog = null
        if (mAlbumDialogDialogPresenter != null) {
            mAlbumDialogDialogPresenter?.dismiss()
        }
        albumDialog = null
    }

    fun showHiddenDialogs(context: Context) {
        if (mLoginDialogDialogPresenter != null) {
            loginDialog = LoginDialog(context)
            mLoginDialogDialogPresenter?.takeView(loginDialog)
            mLoginDialogDialogPresenter?.show()
        }
        if (mAlbumDialogDialogPresenter != null) {
            albumDialog = AlbumDialog(context)
            mAlbumDialogDialogPresenter?.takeView(albumDialog)
            mAlbumDialogDialogPresenter?.show()
        }
    }
}
