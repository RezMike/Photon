package io.github.rezmike.foton.ui.screens.upload

import android.content.Context
import android.util.AttributeSet
import io.github.rezmike.foton.ui.abstracts.AbstractView
import io.github.rezmike.foton.utils.DaggerService

class UploadView(context: Context, attrs: AttributeSet?) : AbstractView<UploadPresenter, UploadView>(context, attrs) {

    override fun initDagger(context: Context) {
        DaggerService.getDaggerComponent<UploadScreen.Component>(context).inject(this)
    }
}
