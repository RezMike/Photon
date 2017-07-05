package io.github.rezmike.foton.ui.dialogs.login

import android.text.TextUtils
import android.util.Patterns
import io.github.rezmike.foton.data.network.req.SingInReq
import io.github.rezmike.foton.data.storage.dto.DialogResult
import io.github.rezmike.foton.data.storage.dto.LoginInfoDto
import io.github.rezmike.foton.ui.activities.root.AccountModel
import io.github.rezmike.foton.utils.isEmailValid
import io.github.rezmike.foton.utils.isPasswordValid
import mortar.PopupPresenter


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
            getDialog().hideEmailError()
        } else {
            getDialog().showEmailError()
        }

    }

    fun checkPassword(password: String) {
        this.password = password
        if (password.isPasswordValid()) {
            getDialog().hidePasswordError()
        } else {
            getDialog().showPasswordError()
        }
    }

    fun onClickOk() {
        if (email.isEmailValid() && password.isPasswordValid()) {
            model.signIn(SingInReq(email, password))
                    .subscribe({
                        view.dismiss(true)
                        onResult(DialogResult(true))
                    }, {
                        view.dismiss(false)
                        getDialog().showErrorSignIn(it)
                    })
        } else {
            view.dismiss(false)
        }
    }

    fun onClickCancel() {
        view.dismiss(true)
    }

    override fun onPopupResult(result: DialogResult) {}

    fun getDialog() = view as LoginDialog
}
