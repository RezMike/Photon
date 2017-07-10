package io.github.rezmike.foton.ui.screens.search

import android.content.Context
import android.util.AttributeSet
import io.github.rezmike.foton.ui.abstracts.AbstractView
import io.github.rezmike.foton.utils.DaggerService

class SearchView(context: Context, attrs: AttributeSet?) : AbstractView<SearchPresenter, SearchView>(context, attrs) {

    override fun initDagger(context: Context) {
        DaggerService.getDaggerComponent<SearchScreen.Component>(context).inject(this)
    }

    override fun onBackPressed(): Boolean {
        return false
    }
}
