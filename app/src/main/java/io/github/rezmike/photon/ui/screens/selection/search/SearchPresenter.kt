package io.github.rezmike.photon.ui.screens.selection.search

import io.github.rezmike.photon.ui.screens.AbstractPresenter
import io.github.rezmike.photon.utils.DaggerService
import mortar.MortarScope

class SearchPresenter : AbstractPresenter<SearchView, SearchModel, SearchPresenter>() {

    override fun initDagger(scope: MortarScope) {
        DaggerService.getDaggerComponent<SearchScreen.Component>(scope).inject(this)
    }

    override fun initActionBar() {
        // do nothing
    }
}
