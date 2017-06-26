package io.github.rezmike.foton.ui.screens.auth

import android.content.Context
import android.util.AttributeSet
import io.github.rezmike.foton.ui.abstracts.AbstractView
import io.github.rezmike.foton.utils.DaggerService

class AuthView(context: Context, attrs: AttributeSet?) : AbstractView<AuthPresenter, AuthView>(context, attrs) {

    override fun initDagger(context: Context) {
        DaggerService.getDaggerComponent<AuthScreen.Component>(context).inject(this)
    }

    override fun onBackPressed(): Boolean {
        return false
    }
}
