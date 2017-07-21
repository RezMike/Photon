package io.github.rezmike.photon.ui.screens.add_info

import android.content.Context
import android.util.AttributeSet
import io.github.rezmike.photon.ui.screens.AbstractView
import io.github.rezmike.photon.utils.DaggerService

class AddInfoView(context: Context, attrs: AttributeSet?) : AbstractView<AddInfoPresenter, AddInfoView>(context, attrs) {

    override fun initDagger(context: Context) {
        DaggerService.getDaggerComponent<AddInfoScreen.Component>(context).inject(this)
    }

    override fun onBackPressed(): Boolean {
        return false
    }
}
