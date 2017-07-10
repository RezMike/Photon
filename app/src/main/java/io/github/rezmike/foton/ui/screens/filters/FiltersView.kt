package io.github.rezmike.foton.ui.screens.filters

import android.content.Context
import android.util.AttributeSet
import io.github.rezmike.foton.ui.abstracts.AbstractView
import io.github.rezmike.foton.utils.DaggerService

class FiltersView(context: Context, attrs: AttributeSet?) : AbstractView<FiltersPresenter, FiltersView>(context, attrs) {

    override fun initDagger(context: Context) {
        DaggerService.getDaggerComponent<FiltersScreen.Component>(context).inject(this)
    }

    override fun onBackPressed(): Boolean {
        return false
    }
}
