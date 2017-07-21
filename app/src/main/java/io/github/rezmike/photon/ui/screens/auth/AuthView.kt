package io.github.rezmike.photon.ui.screens.auth

import android.content.Context
import android.util.AttributeSet
import io.github.rezmike.photon.ui.screens.AbstractView
import io.github.rezmike.photon.utils.DaggerService
import kotlinx.android.synthetic.main.screen_auth.view.*

class AuthView(context: Context, attrs: AttributeSet?) : AbstractView<AuthPresenter, AuthView>(context, attrs) {

    override fun initDagger(context: Context) {
        DaggerService.getDaggerComponent<AuthScreen.Component>(context).inject(this)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        sign_up_btn.setOnClickListener { presenter.onClickRegister() }
        sign_in_btn.setOnClickListener { presenter.onClickLogin() }
    }

    override fun onBackPressed(): Boolean {
        return false
    }
}
