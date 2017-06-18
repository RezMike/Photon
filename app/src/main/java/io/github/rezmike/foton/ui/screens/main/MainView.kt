package io.github.rezmike.foton.ui.screens.main

import android.content.Context
import android.util.AttributeSet
import io.github.rezmike.foton.ui.abstracts.AbstractView
import io.github.rezmike.foton.utils.DaggerService

class MainView(context: Context, attrs: AttributeSet?) : AbstractView<MainPresenter, MainView>(context, attrs) {

    override fun initDagger(context: Context) {
        DaggerService.getDaggerComponent<MainScreen.Component>(context).inject(this)
    }
}
