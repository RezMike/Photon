package io.github.rezmike.foton.ui.screens.login

import android.content.Context
import android.util.AttributeSet
import io.github.rezmike.foton.ui.abstracts.AbstractView
import io.github.rezmike.foton.utils.DaggerService

class LoginView(context: Context, attrs: AttributeSet?) : AbstractView<LoginPresenter, LoginView>(context, attrs) {

    override fun initDagger(context: Context) {
        DaggerService.getDaggerComponent<LoginScreen.Component>(context).inject(this)
    }

    override fun onBackPressed(): Boolean {
        return false
    }
}
