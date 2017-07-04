package io.github.rezmike.foton.ui.dialogs.login

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import io.github.rezmike.foton.R
import io.github.rezmike.foton.data.storage.dto.DialogResult
import io.github.rezmike.foton.data.storage.dto.LoginInfoDto
import io.github.rezmike.foton.ui.others.CustomTextWatcher
import kotlinx.android.synthetic.main.dialog_footer.view.*
import kotlinx.android.synthetic.main.screen_register.view.*
import mortar.Popup
import mortar.PopupPresenter

class LoginDialog(context: Context) : Popup<LoginInfoDto, DialogResult> {

    private val rootContext = context
    private var dialog: AlertDialog? = null

    override fun show(info: LoginInfoDto?, withFlourish: Boolean, presenter: PopupPresenter<LoginInfoDto, DialogResult>) {
        val view = LayoutInflater.from(rootContext).inflate(R.layout.dialog_login, null, false)
        onFinishInflate(view, presenter as LoginPresenter, info)
        dialog = AlertDialog.Builder(rootContext)
                .setView(view)
                .setOnCancelListener { presenter.onDismissed(DialogResult(false)) }
                .setOnDismissListener { presenter.onDismissed(DialogResult(false)) }
                .create()
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        dialog?.show()
    }

    private fun onFinishInflate(view: View, presenter: LoginPresenter, info: LoginInfoDto?) = with(view) {
        email_et.setText(info?.email ?: "")
        password_et.setText(info?.password ?: "")
        email_et.addTextChangedListener(object : CustomTextWatcher() {
            override fun afterTextChanged(s: Editable) {
                presenter.checkEmail(s.toString())
            }
        })
        password_et.addTextChangedListener(object : CustomTextWatcher() {
            override fun afterTextChanged(s: Editable) {
                presenter.checkPassword(s.toString())
            }
        })
        ok_btn.setOnClickListener {
            dialog?.cancel()
            presenter.onClickOk()
        }
        cancel_btn.setOnClickListener {
            dialog?.cancel()
            presenter.onClickCancel()
        }
    }

    override fun dismiss(withFlourish: Boolean) {
        dialog?.dismiss()
        dialog = null
    }

    override fun isShowing() = dialog != null

    override fun getContext() = rootContext
}
