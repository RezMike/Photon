package io.github.rezmike.foton.ui.screens.register

import android.content.Context
import android.util.AttributeSet
import io.github.rezmike.foton.ui.abstracts.AbstractView
import io.github.rezmike.foton.utils.DaggerService

class RegisterView(context: Context, attrs: AttributeSet?) : AbstractView<RegisterPresenter, RegisterView>(context, attrs) {

    override fun initDagger(context: Context) {
        DaggerService.getDaggerComponent<RegisterScreen.Component>(context).inject(this)
    }
}
