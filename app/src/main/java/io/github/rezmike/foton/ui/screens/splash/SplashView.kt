package io.github.rezmike.foton.ui.screens.splash

import android.content.Context
import android.util.AttributeSet
import io.github.rezmike.foton.ui.abstracts.AbstractView
import io.github.rezmike.foton.utils.DaggerService

class SplashView(context: Context, attrs: AttributeSet?) : AbstractView<SplashPresenter, SplashView>(context, attrs) {

    override fun initDagger(context: Context) {
        DaggerService.getDaggerComponent<SplashScreen.Component>(context).inject(this)
    }

}