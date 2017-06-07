package io.github.rezmike.foton.ui.screens.photocard

import android.content.Context
import android.util.AttributeSet
import io.github.rezmike.foton.ui.abstracts.AbstractView
import io.github.rezmike.foton.utils.DaggerService

class PhotocardView(context: Context, attrs: AttributeSet?) : AbstractView<PhotocardPresenter, PhotocardView>(context, attrs) {

    override fun initDagger(context: Context) {
        DaggerService.getDaggerComponent<PhotocardScreen.Component>(context).inject(this)
    }
}
