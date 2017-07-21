package io.github.rezmike.photon.ui.dialogs.register

import android.content.Context
import android.text.Editable
import android.view.View
import android.widget.Button
import android.widget.EditText
import io.github.rezmike.photon.R
import io.github.rezmike.photon.data.storage.dto.DialogResult
import io.github.rezmike.photon.data.storage.dto.RegisterInfoDto
import io.github.rezmike.photon.ui.dialogs.AbstractDialog
import io.github.rezmike.photon.ui.others.AnimHelper
import io.github.rezmike.photon.ui.others.CustomTextWatcher
import io.github.rezmike.photon.ui.others.changeError
import mortar.PopupPresenter

class RegisterDialog(context: Context) : AbstractDialog<RegisterInfoDto>(context) {

    private var loginEt: EditText? = null
    private var emailEt: EditText? = null
    private var nameEt: EditText? = null
    private var passwordEt: EditText? = null
    private var okBtn: Button? = null
    private var cancelBtn: Button? = null

    override fun getLayoutRes() = R.layout.dialog_register

    override fun onFinishInflate(view: View, presenter: PopupPresenter<RegisterInfoDto, DialogResult>, info: RegisterInfoDto) {
        if (presenter !is RegisterDialogPresenter) {
            throw ClassCastException("Presenter must be RegisterDialogPresenter")
        }
        loginEt = view.findViewById(R.id.login_et) as EditText
        emailEt = view.findViewById(R.id.email_et) as EditText
        nameEt = view.findViewById(R.id.name_et) as EditText
        passwordEt = view.findViewById(R.id.password_et) as EditText
        okBtn = view.findViewById(R.id.ok_btn) as Button
        cancelBtn = view.findViewById(R.id.cancel_btn) as Button

        loginEt?.addTextChangedListener(object : CustomTextWatcher() {
            override fun afterTextChanged(s: Editable) {
                presenter.checkLogin(s.toString())
            }
        })
        emailEt?.addTextChangedListener(object : CustomTextWatcher() {
            override fun afterTextChanged(s: Editable) {
                presenter.checkEmail(s.toString())
            }
        })
        nameEt?.addTextChangedListener(object : CustomTextWatcher() {
            override fun afterTextChanged(s: Editable) {
                presenter.checkName(s.toString())
            }
        })
        passwordEt?.addTextChangedListener(object : CustomTextWatcher() {
            override fun afterTextChanged(s: Editable) {
                presenter.checkPassword(s.toString())
            }
        })
        loginEt?.setText(info.login)
        emailEt?.setText(info.email)
        nameEt?.setText(info.name)
        passwordEt?.setText(info.password)

        okBtn?.setOnClickListener { presenter.onClickOk() }
        cancelBtn?.setOnClickListener { presenter.onClickCancel() }
    }

    override fun onDialogDismiss() {
        loginEt = null
        emailEt = null
        nameEt = null
        passwordEt = null
        okBtn = null
        cancelBtn = null
    }

    fun showLoginError() {
        loginEt?.changeError(true, context.getString(R.string.register_dialog_login_error))
    }

    fun hideLoginError() {
        loginEt?.changeError(false)
    }

    fun showEmailError() {
        emailEt?.changeError(true, context.getString(R.string.register_dialog_email_error))
    }

    fun hideEmailError() {
        emailEt?.changeError(false)
    }

    fun showNameError() {
        nameEt?.changeError(true, context.getString(R.string.register_dialog_name_error))
    }

    fun hideNameError() {
        nameEt?.changeError(false)
    }

    fun showPasswordError() {
        passwordEt?.changeError(true, context.getString(R.string.register_dialog_password_error))
    }

    fun hidePasswordError() {
        passwordEt?.changeError(false)
    }

    fun accentLogin() {
        AnimHelper.accentAnim(loginEt!!)
    }

    fun accentEmail() {
        AnimHelper.accentAnim(emailEt!!)
    }

    fun accentName() {
        AnimHelper.accentAnim(nameEt!!)
    }

    fun accentPassword() {
        AnimHelper.accentAnim(passwordEt!!)
    }
}
