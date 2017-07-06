package io.github.rezmike.foton.ui.dialogs.login

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.widget.CardView
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.Toast
import io.github.rezmike.foton.R
import io.github.rezmike.foton.data.storage.dto.DialogResult
import io.github.rezmike.foton.data.storage.dto.LoginInfoDto
import io.github.rezmike.foton.ui.others.AnimHelper
import io.github.rezmike.foton.ui.others.CustomTextWatcher
import io.github.rezmike.foton.ui.others.changeError
import kotlinx.android.synthetic.main.dialog_footer.view.*
import mortar.Popup
import mortar.PopupPresenter

class LoginDialog(context: Context) : Popup<LoginInfoDto, DialogResult> {

    private val rootContext = context
    private var dialog: AlertDialog? = null
    private var emailEt: EditText? = null
    private var passwordEt: EditText? = null
    private var card: CardView? = null

    override fun show(info: LoginInfoDto?, withFlourish: Boolean, presenter: PopupPresenter<LoginInfoDto, DialogResult>) {
        val view = LayoutInflater.from(rootContext).inflate(R.layout.dialog_login, null, false)
        onFinishInflate(view, presenter as LoginPresenter, info)
        dialog = AlertDialog.Builder(rootContext)
                .setView(view)
                .setOnCancelListener { presenter.onDismissed(DialogResult(false)) }
                .setOnDismissListener { presenter.onDismissed(DialogResult(false)) }
                .create()
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.show()
    }

    private fun onFinishInflate(view: View, presenter: LoginPresenter, info: LoginInfoDto?) = with(view) {
        emailEt = view.findViewById(R.id.email_et) as EditText
        passwordEt = view.findViewById(R.id.password_et) as EditText
        card = view.findViewById(R.id.card) as CardView
        emailEt?.addTextChangedListener(object : CustomTextWatcher() {
            override fun afterTextChanged(s: Editable) {
                presenter.checkEmail(s.toString())
            }
        })
        passwordEt?.addTextChangedListener(object : CustomTextWatcher() {
            override fun afterTextChanged(s: Editable) {
                presenter.checkPassword(s.toString())
            }
        })
        emailEt?.setText(info?.email ?: "")
        passwordEt?.setText(info?.password ?: "")
        ok_btn.setOnClickListener {
            presenter.onClickOk()
        }
        cancel_btn.setOnClickListener {
            presenter.onClickCancel()
        }
    }

    fun showEmailError() {
        emailEt?.changeError(true, context.getString(R.string.login_email_error))
    }

    fun hideEmailError() {
        emailEt?.changeError(false)
    }

    fun showPasswordError() {
        passwordEt?.changeError(true, context.getString(R.string.login_password_error))
    }

    fun hidePasswordError() {
        passwordEt?.changeError(false)
    }

    fun accentEmail() {
        AnimHelper.accentAnim(emailEt!!)
    }

    fun accentPassword() {
        AnimHelper.accentAnim(passwordEt!!)
    }

    fun accentFields() {
        accentEmail()
        accentPassword()
    }

    fun showError(error: Throwable) {
        showError(error.message ?: context.getString(R.string.error_unknown))
    }

    fun showError(errorMessage: String) {
        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
    }

    fun dismiss() {
        emailEt = null
        passwordEt = null
        card = null
        dialog?.dismiss()
        dialog = null
    }

    override fun isShowing() = dialog != null

    override fun getContext() = rootContext

    override fun dismiss(withFlourish: Boolean) {
        dismiss()
    }
}
