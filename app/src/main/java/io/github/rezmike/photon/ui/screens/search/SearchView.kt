package io.github.rezmike.photon.ui.screens.search

import android.content.Context
import android.util.AttributeSet
import io.github.rezmike.photon.ui.screens.AbstractView
import io.github.rezmike.photon.utils.DaggerService

class SearchView(context: Context, attrs: AttributeSet?) : AbstractView<SearchPresenter, SearchView>(context, attrs) {

    override fun initDagger(context: Context) {
        DaggerService.getDaggerComponent<SearchScreen.Component>(context).inject(this)
    }

    override fun onBackPressed(): Boolean {
        return false
    }
}
