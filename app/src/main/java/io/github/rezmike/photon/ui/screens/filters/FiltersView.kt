package io.github.rezmike.photon.ui.screens.filters

import android.content.Context
import android.util.AttributeSet
import io.github.rezmike.photon.ui.abstracts.AbstractView
import io.github.rezmike.photon.utils.DaggerService

class FiltersView(context: Context, attrs: AttributeSet?) : AbstractView<FiltersPresenter, FiltersView>(context, attrs) {

    override fun initDagger(context: Context) {
        DaggerService.getDaggerComponent<FiltersScreen.Component>(context).inject(this)
    }

    override fun onBackPressed(): Boolean {
        return false
    }
}
