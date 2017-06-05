package io.github.rezmike.foton.ui.screens.splash

import android.content.Context
import android.util.AttributeSet
import io.github.rezmike.foton.mortar.ScreenScoper
import io.github.rezmike.foton.ui.abstracts.AbstractView

class SplashView(context: Context?, attrs: AttributeSet?) : AbstractView<SplashPresenter, SplashView>(context, attrs) {


    override fun initDagger(context: Context?) {
        ScreenScoper.getDaggerComponent<SplashScreen.Component>(context!!).inject(this)
    }

}