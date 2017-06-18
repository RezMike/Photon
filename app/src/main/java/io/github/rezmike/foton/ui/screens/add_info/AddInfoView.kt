package io.github.rezmike.foton.ui.screens.add_info

import android.content.Context
import android.util.AttributeSet
import io.github.rezmike.foton.ui.abstracts.AbstractView
import io.github.rezmike.foton.utils.DaggerService

class AddInfoView(context: Context, attrs: AttributeSet?) : AbstractView<AddInfoPresenter, AddInfoView>(context, attrs) {

    override fun initDagger(context: Context) {
        DaggerService.getDaggerComponent<AddInfoScreen.Component>(context).inject(this)
    }
}
