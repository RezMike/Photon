package io.github.rezmike.photon.ui.dialogs.login

import io.github.rezmike.photon.R
import io.github.rezmike.photon.data.network.error.AccessError
import io.github.rezmike.photon.data.network.error.NotFoundError
import io.github.rezmike.photon.data.network.req.LoginReq
import io.github.rezmike.photon.data.storage.dto.DialogResult
import io.github.rezmike.photon.data.storage.dto.LoginInfoDto
import io.github.rezmike.photon.ui.activities.root.AccountModel
import io.github.rezmike.photon.ui.others.isEmailValid
import io.github.rezmike.photon.ui.others.isPasswordValid
import mortar.PopupPresenter
import rx.android.schedulers.AndroidSchedulers

class LoginPresenter(val model: AccountModel) : PopupPresenter<LoginInfoDto, DialogResult>() {

    private var onResult: (DialogResult) -> Unit = {}

    private var email: String = ""
    private var password: String = ""

    fun show() = show(LoginInfoDto(email, password))

    fun setOnResultListener(listener: (DialogResult) -> Unit) {
        onResult = listener
    }

    fun checkEmail(email: String) {
        this.email = email
        if (email.isEmailValid()) {
            getDialog()?.hideEmailError()
        } else {
            getDialog()?.showEmailError()
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

    fun onClickOk() {
        if (email.isEmpty() || password.isEmpty()) {
            if (email.isEmpty()) getDialog()?.accentEmail()
            if (password.isEmpty()) getDialog()?.accentPassword()
            getDialog()?.showMessage(R.string.login_error_empty_fields)
        } else if (email.isEmailValid() && password.isPasswordValid()) {
            model.login(LoginReq(email, password))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        getDialog()?.showMessage(R.string.auth_success)
                        getDialog()?.dismiss()
                        onResult(DialogResult(true))
                    }, {
                        if (it is AccessError || it is NotFoundError) {
                            getDialog()?.showMessage(R.string.login_error_incorrect_data)
                            getDialog()?.accentFields()
                        } else {
                            getDialog()?.showError(it)
                        }
                    })
        } else {
            if (!email.isEmailValid()) getDialog()?.accentEmail()
            if (!password.isPasswordValid()) getDialog()?.accentPassword()
        }
    }

    fun onClickCancel() {
        getDialog()?.dismiss()
        onResult(DialogResult(false))
    }

    override fun onPopupResult(result: DialogResult) {
        getDialog()?.dismiss()
        onResult(result)
    }

    fun getDialog(): LoginDialog? = view as LoginDialog
}
