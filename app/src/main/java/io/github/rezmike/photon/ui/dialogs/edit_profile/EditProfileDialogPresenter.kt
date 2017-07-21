package io.github.rezmike.photon.ui.dialogs.edit_profile

import io.github.rezmike.photon.R
import io.github.rezmike.photon.data.storage.dto.EditProfileInfoDto
import io.github.rezmike.photon.ui.activities.root.AccountModel
import io.github.rezmike.photon.ui.dialogs.AbstractDialogPresenter
import io.github.rezmike.photon.ui.others.isLoginValid
import io.github.rezmike.photon.ui.others.isNameValid
import rx.android.schedulers.AndroidSchedulers

class EditProfileDialogPresenter(val model: AccountModel) : AbstractDialogPresenter<EditProfileInfoDto, EditProfileDialog>() {

    private var name: String = model.getUserName()!!
    private var login: String = model.getUserLogin()!!

    override fun show() = show(EditProfileInfoDto(name, login))

    fun checkName(name: String) {
        this.name = name
        if (name.isNameValid()) {
            getDialog()?.hideNameError()
        } else {
            getDialog()?.showNameError()
        }
    }

    fun checkLogin(login: String) {
        this.login = login
        if (login.isLoginValid()) {
            getDialog()?.hideLoginError()
        } else {
            getDialog()?.showLoginError()
        }
    }

    override fun onClickOk() {
        if (name.isEmpty() || login.isEmpty()) {
            if (name.isEmpty()) getDialog()?.accentName()
            if (login.isEmpty()) getDialog()?.accentLogin()
            getDialog()?.showMessage(R.string.edit_profile_dialog_error_empty_fields)
        } else if (name.isNameValid() && login.isLoginValid()) {
            model.updateProfileInfo(name, login)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        getDialog()?.showMessage(R.string.edit_profile_dialog_success)
                        onDialogResult(true)
                    }, {
                        // TODO: 19.07.2017 show error when api fixes
                        getDialog()?.showError(it)
                    })
        } else {
            if (!name.isNameValid()) getDialog()?.accentName()
            if (!login.isLoginValid()) getDialog()?.accentLogin()
        }
    }
}