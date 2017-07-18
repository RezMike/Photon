package io.github.rezmike.photon.ui.dialogs.register

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.Toast
import io.github.rezmike.photon.R
import io.github.rezmike.photon.data.storage.dto.DialogResult
import io.github.rezmike.photon.data.storage.dto.RegisterInfoDto
import io.github.rezmike.photon.ui.others.AnimHelper
import io.github.rezmike.photon.ui.others.CustomTextWatcher
import io.github.rezmike.photon.ui.others.changeError
import kotlinx.android.synthetic.main.dialog_footer.view.*
import mortar.Popup
import mortar.PopupPresenter

class RegisterDialog(context: Context) : Popup<RegisterInfoDto, DialogResult> {

    private val rootContext = context
    private var dialog: AlertDialog? = null
    private var loginEt: EditText? = null
    private var emailEt: EditText? = null
    private var nameEt: EditText? = null
    private var passwordEt: EditText? = null

    override fun show(info: RegisterInfoDto, withFlourish: Boolean, presenter: PopupPresenter<RegisterInfoDto, DialogResult>) {
        val view = LayoutInflater.from(rootContext).inflate(R.layout.dialog_register, null, false)
        onFinishInflate(view, presenter as RegisterPresenter, info)
        dialog = AlertDialog.Builder(rootContext)
                .setView(view)
                .setOnCancelListener { presenter.onDismissed(DialogResult(false)) }
                .create()
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.show()
    }

    private fun onFinishInflate(view: View, presenter: RegisterPresenter, info: RegisterInfoDto) = with(view) {
        loginEt = view.findViewById(R.id.login_et) as EditText
        emailEt = view.findViewById(R.id.email_et) as EditText
        nameEt = view.findViewById(R.id.name_et) as EditText
        passwordEt = view.findViewById(R.id.password_et) as EditText
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
        ok_btn.setOnClickListener {
            presenter.onClickOk()
        }
        cancel_btn.setOnClickListener {
            presenter.onClickCancel()
        }
    }

    fun showLoginError() {
        loginEt?.changeError(true, context.getString(R.string.register_login_error))
    }

    fun hideLoginError() {
        loginEt?.changeError(false)
    }

    fun showEmailError() {
        emailEt?.changeError(true, context.getString(R.string.register_email_error))
    }

    fun hideEmailError() {
        emailEt?.changeError(false)
    }

    fun showNameError() {
        nameEt?.changeError(true, context.getString(R.string.register_name_error))
    }

    fun hideNameError() {
        nameEt?.changeError(false)
    }

    fun showPasswordError() {
        passwordEt?.changeError(true, context.getString(R.string.register_password_error))
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

    fun accentFields() {
        accentLogin()
        accentEmail()
        accentName()
        accentPassword()
    }

    fun showError(error: Throwable) {
        showMessage(error.message ?: context.getString(R.string.error_unknown))
    }

    fun showMessage(stringResId: Int) {
        showMessage(context.getString(stringResId))
    }

    fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun dismiss() {
        loginEt = null
        emailEt = null
        nameEt = null
        passwordEt = null
        dialog?.dismiss()
        dialog = null
    }

    override fun isShowing() = dialog != null

    override fun getContext() = rootContext

    override fun dismiss(withFlourish: Boolean) {
        dismiss()
    }
}
