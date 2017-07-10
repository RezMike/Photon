package io.github.rezmike.photon.ui.screens.register

import android.content.Context
import android.util.AttributeSet
import io.github.rezmike.photon.ui.abstracts.AbstractView
import io.github.rezmike.photon.utils.DaggerService

class RegisterView(context: Context, attrs: AttributeSet?) : AbstractView<RegisterPresenter, RegisterView>(context, attrs) {

    override fun initDagger(context: Context) {
        DaggerService.getDaggerComponent<RegisterScreen.Component>(context).inject(this)
    }

    override fun onBackPressed(): Boolean {
        return false
    }
}
