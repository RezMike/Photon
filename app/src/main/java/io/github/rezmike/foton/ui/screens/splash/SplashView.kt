package io.github.rezmike.foton.ui.screens.splash

import android.content.Context
import android.util.AttributeSet
import flow.Flow
import io.github.rezmike.foton.ui.abstracts.AbstractView
import io.github.rezmike.foton.ui.screens.main.MainScreen
import io.github.rezmike.foton.utils.DaggerService
import kotlinx.android.synthetic.main.screen_splash.view.*

class SplashView(context: Context, attrs: AttributeSet?) : AbstractView<SplashPresenter, SplashView>(context, attrs) {

    override fun initDagger(context: Context) {
        DaggerService.getDaggerComponent<SplashScreen.Component>(context).inject(this)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        progress.smoothToShow()
        presenter.init()
    }

    override fun onDetachedFromWindow() {
        progress.smoothToHide()
        super.onDetachedFromWindow()
    }

    fun showMainScreen() {
        Flow.get(this).set(MainScreen())
    }
}