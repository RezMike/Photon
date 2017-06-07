package io.github.rezmike.foton.ui.screens

import android.content.Context
import android.util.AttributeSet
import io.github.rezmike.foton.ui.abstracts.AbstractView
import io.github.rezmike.foton.ui.screens.DefaultPresenter
import io.github.rezmike.foton.ui.screens.DefaultScreen
import io.github.rezmike.foton.utils.DaggerService

class DefaultView(context: Context, attrs: AttributeSet?) : AbstractView<DefaultPresenter, DefaultView>(context, attrs) {

    override fun initDagger(context: Context) {
        DaggerService.getDaggerComponent<DefaultScreen.Component>(context).inject(this)
    }
}
