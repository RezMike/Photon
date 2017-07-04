package io.github.rezmike.foton.ui.dialogs.login

import io.github.rezmike.foton.data.storage.dto.DialogResult
import io.github.rezmike.foton.data.storage.dto.LoginInfoDto
import mortar.PopupPresenter

class LoginPresenter : PopupPresenter<LoginInfoDto, DialogResult>() {

    private var onResult: (DialogResult) -> Unit = {}

    private var email: String = ""
    private var password: String = ""

    fun show() = show(LoginInfoDto(email, password))

    fun setOnResultListener(listener: (DialogResult) -> Unit) {
        onResult = listener
    }

    fun checkEmail(email: String) {
        this.email = email
    }

    fun checkPassword(password: String) {
        this.password = password
    }

    fun onClickOk() {
        onResult(DialogResult(true))
    }

    fun onClickCancel() {
        onResult(DialogResult(false))
    }

    override fun onPopupResult(result: DialogResult) {}
}
