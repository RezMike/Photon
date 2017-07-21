package io.github.rezmike.photon.ui.dialogs

import android.content.Context
import io.github.rezmike.photon.data.storage.dto.DialogResult
import io.github.rezmike.photon.data.storage.realm.AlbumRealm
import io.github.rezmike.photon.ui.activities.root.AccountModel
import io.github.rezmike.photon.ui.activities.root.RootPresenter
import io.github.rezmike.photon.ui.dialogs.album.AlbumDialog
import io.github.rezmike.photon.ui.dialogs.album.AlbumDialogPresenter
import io.github.rezmike.photon.ui.dialogs.avatar.AvatarDialog
import io.github.rezmike.photon.ui.dialogs.avatar.AvatarDialogPresenter
import io.github.rezmike.photon.ui.dialogs.edit_profile.EditProfileDialog
import io.github.rezmike.photon.ui.dialogs.edit_profile.EditProfileDialogPresenter
import io.github.rezmike.photon.ui.dialogs.login.LoginDialog
import io.github.rezmike.photon.ui.dialogs.login.LoginDialogPresenter
import io.github.rezmike.photon.ui.dialogs.register.RegisterDialog
import io.github.rezmike.photon.ui.dialogs.register.RegisterDialogPresenter

class DialogManager(val rootPresenter: RootPresenter, val model: AccountModel) {

    private var loginDialogPresenter: LoginDialogPresenter? = null
    private var loginDialog: LoginDialog? = null

    private var registerDialogPresenter: RegisterDialogPresenter? = null
    private var registerDialog: RegisterDialog? = null

    private var albumDialogPresenter: AlbumDialogPresenter? = null
    private var albumDialog: AlbumDialog? = null

    private var editProfileDialogPresenter: EditProfileDialogPresenter? = null
    private var editProfileDialog: EditProfileDialog? = null

    private var avatarDialogPresenter: AvatarDialogPresenter? = null
    private var avatarDialog: AvatarDialog? = null

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

    fun showRegisterDialog(context: Context, onResult: (DialogResult) -> Unit = {}) {
        registerDialogPresenter = RegisterDialogPresenter(model)
        registerDialogPresenter?.setOnResultListener {
            registerDialogPresenter = null
            registerDialog = null
            onResult(it)
        }
        registerDialog = RegisterDialog(context)
        registerDialogPresenter?.takeView(registerDialog)
        registerDialogPresenter?.show()
    }

    fun showAlbumDialog(context: Context, onResult: (DialogResult) -> Unit = {}, album: AlbumRealm?) {
        albumDialogPresenter = AlbumDialogPresenter(model, album)
        albumDialogPresenter?.setOnResultListener {
            albumDialogPresenter = null
            albumDialog = null
            onResult(it)
        }
        albumDialog = AlbumDialog(context, albumDialogPresenter!!.isEdit())
        albumDialogPresenter?.takeView(albumDialog)
        albumDialogPresenter?.show()
    }

    fun showEditProfileDialog(context: Context, onResult: (DialogResult) -> Unit) {
        editProfileDialogPresenter = EditProfileDialogPresenter(model)
        editProfileDialogPresenter?.setOnResultListener {
            editProfileDialogPresenter = null
            editProfileDialog = null
            onResult(it)
        }
        editProfileDialog = EditProfileDialog(context)
        editProfileDialogPresenter?.takeView(editProfileDialog)
        editProfileDialogPresenter?.show()
    }

    fun showAvatarDialog(context: Context, onResult: (DialogResult) -> Unit) {
        avatarDialogPresenter = AvatarDialogPresenter(rootPresenter, model)
        avatarDialogPresenter?.setOnResultListener {
            avatarDialogPresenter = null
            avatarDialog = null
            onResult(it)
        }
        avatarDialog = AvatarDialog(context)
        avatarDialogPresenter?.takeView(avatarDialog)
        avatarDialogPresenter?.show()
    }

    fun dismissDialogs() {
        if (loginDialogPresenter != null) {
            loginDialogPresenter?.dismiss()
        }
        loginDialog = null
        if (registerDialogPresenter != null) {
            registerDialogPresenter?.dismiss()
        }
        registerDialog = null
        if (albumDialogPresenter != null) {
            albumDialogPresenter?.dismiss()
        }
        albumDialog = null
        if (editProfileDialogPresenter != null) {
            editProfileDialogPresenter?.dismiss()
        }
        editProfileDialog = null
        if (avatarDialogPresenter != null) {
            avatarDialogPresenter?.dismiss()
        }
        avatarDialog = null
    }

    fun showHiddenDialogs(context: Context) {
        if (loginDialogPresenter != null) {
            loginDialog = LoginDialog(context)
            loginDialogPresenter?.takeView(loginDialog)
            loginDialogPresenter?.show()
        }
        if (registerDialogPresenter != null) {
            registerDialog = RegisterDialog(context)
            registerDialogPresenter?.takeView(registerDialog)
            registerDialogPresenter?.show()
        }
        if (albumDialogPresenter != null) {
            albumDialog = AlbumDialog(context, albumDialogPresenter!!.isEdit())
            albumDialogPresenter?.takeView(albumDialog)
            albumDialogPresenter?.show()
        }
        if (editProfileDialogPresenter != null) {
            editProfileDialog = EditProfileDialog(context)
            editProfileDialogPresenter?.takeView(editProfileDialog)
            editProfileDialogPresenter?.show()
        }
        if (avatarDialogPresenter != null) {
            avatarDialog = AvatarDialog(context)
            avatarDialogPresenter?.takeView(avatarDialog)
            avatarDialogPresenter?.show()
        }
    }
}
