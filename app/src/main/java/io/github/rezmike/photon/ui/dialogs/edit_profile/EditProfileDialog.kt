package io.github.rezmike.photon.ui.dialogs.edit_profile

import android.content.Context
import android.view.View
import android.widget.Button
import android.widget.EditText
import io.github.rezmike.photon.R
import io.github.rezmike.photon.data.storage.dto.DialogResult
import io.github.rezmike.photon.data.storage.dto.EditProfileInfoDto
import io.github.rezmike.photon.ui.dialogs.AbstractDialog
import io.github.rezmike.photon.ui.others.AnimHelper
import io.github.rezmike.photon.ui.others.addAfterTextChangedListener
import io.github.rezmike.photon.ui.others.changeError
import mortar.PopupPresenter

class EditProfileDialog(context: Context) : AbstractDialog<EditProfileInfoDto>(context) {

    private var nameEt: EditText? = null
    private var loginEt: EditText? = null
    private var okBtn: Button? = null
    private var cancelBtn: Button? = null

    override fun getLayoutRes() = R.layout.dialog_edit_profile

    override fun onFinishInflate(view: View, presenter: PopupPresenter<EditProfileInfoDto, DialogResult>, info: EditProfileInfoDto) {
        if (presenter !is EditProfileDialogPresenter) {
            throw ClassCastException("Presenter must be EditProfileDialogPresenter")
        }
        nameEt = view.findViewById(R.id.name_et) as EditText
        loginEt = view.findViewById(R.id.login_et) as EditText
        okBtn = view.findViewById(R.id.ok_btn) as Button
        cancelBtn = view.findViewById(R.id.cancel_btn) as Button

        nameEt?.addAfterTextChangedListener { presenter.checkName(it) }
        loginEt?.addAfterTextChangedListener { presenter.checkLogin(it) }

        nameEt?.setText(info.name)
        loginEt?.setText(info.login)

        okBtn?.setOnClickListener { presenter.onClickOk() }
        cancelBtn?.setOnClickListener { presenter.onClickCancel() }
    }

    override fun onDialogDismiss() {
        nameEt = null
        loginEt = null
        okBtn = null
        cancelBtn = null
    }

    fun showNameError() {
        nameEt?.changeError(true, context.getString(R.string.edit_profile_dialog_name_error))
    }

    fun hideNameError() {
        nameEt?.changeError(false)
    }

    fun showLoginError() {
        loginEt?.changeError(true, context.getString(R.string.edit_profile_dialog_login_error))
    }

    fun hideLoginError() {
        loginEt?.changeError(false)
    }

    fun accentName() {
        AnimHelper.accentAnim(nameEt!!)
    }

    fun accentLogin() {
        AnimHelper.accentAnim(loginEt!!)
    }
}