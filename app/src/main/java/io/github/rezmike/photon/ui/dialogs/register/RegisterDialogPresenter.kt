package io.github.rezmike.photon.ui.dialogs.register

import io.github.rezmike.photon.R
import io.github.rezmike.photon.data.storage.dto.DialogResult
import io.github.rezmike.photon.data.storage.dto.RegisterInfoDto
import io.github.rezmike.photon.ui.activities.root.AccountModel
import io.github.rezmike.photon.ui.dialogs.AbstractDialogPresenter
import io.github.rezmike.photon.ui.others.isEmailValid
import io.github.rezmike.photon.ui.others.isLoginValid
import io.github.rezmike.photon.ui.others.isNameValid
import io.github.rezmike.photon.ui.others.isPasswordValid
import rx.android.schedulers.AndroidSchedulers

class RegisterDialogPresenter(val model: AccountModel) : AbstractDialogPresenter<RegisterInfoDto, RegisterDialog>() {

    private var login: String = ""
    private var email: String = ""
    private var name: String = ""
    private var password: String = ""

    override fun show() = show(RegisterInfoDto(login, email, name, password))

    fun checkLogin(login: String) {
        this.login = login
        if (login.isLoginValid()) {
            getDialog()?.hideLoginError()
        } else {
            getDialog()?.showLoginError()
        }
    }

    fun checkEmail(email: String) {
        this.email = email
        if (email.isEmailValid()) {
            getDialog()?.hideEmailError()
        } else {
            getDialog()?.showEmailError()
        }
    }

    fun checkName(name: String) {
        this.name = name
        if (name.isNameValid()) {
            getDialog()?.hideNameError()
        } else {
            getDialog()?.showNameError()
        }
    }

    fun checkPassword(password: String) {
        this.password = password
        if (password.isPasswordValid()) {
            getDialog()?.hidePasswordError()
        } else {
            getDialog()?.showPasswordError()
        }
    }

    override fun onClickOk() {
        if (login.isEmpty() || email.isEmpty() || name.isEmpty() || password.isEmpty()) {
            if (login.isEmpty()) getDialog()?.accentLogin()
            if (email.isEmpty()) getDialog()?.accentEmail()
            if (name.isEmpty()) getDialog()?.accentName()
            if (password.isEmpty()) getDialog()?.accentPassword()
            getDialog()?.showMessage(R.string.register_dialog_error_empty_fields)
        } else if (login.isLoginValid() && email.isEmailValid() && name.isNameValid() && password.isPasswordValid()) {
            model.register(name, login, email, password)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        getDialog()?.showMessage(R.string.auth_success)
                        getDialog()?.dismiss()
                        onResult(DialogResult(true))
                    }, {
                        // TODO: 10.07.2017 implement this
                        /*if (it is AccessError || it is NotFoundError) {
                            getDialog()?.showMessage(R.string.login_error_incorrect_data)
                            getDialog()?.accentFields()
                        } else {*/
                        getDialog()?.showError(it)
                        //}
                    })
        } else {
            if (!login.isLoginValid()) getDialog()?.accentLogin()
            if (!email.isEmailValid()) getDialog()?.accentEmail()
            if (!name.isNameValid()) getDialog()?.accentName()
            if (!password.isPasswordValid()) getDialog()?.accentPassword()
        }
    }
}
