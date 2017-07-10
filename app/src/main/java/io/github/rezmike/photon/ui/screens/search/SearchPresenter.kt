package io.github.rezmike.photon.ui.screens.search

import io.github.rezmike.photon.ui.abstracts.AbstractPresenter
import io.github.rezmike.photon.utils.DaggerService
import mortar.MortarScope

class SearchPresenter : AbstractPresenter<SearchView, SearchModel, SearchPresenter>() {

    override fun initDagger(scope: MortarScope) {
        DaggerService.getDaggerComponent<SearchScreen.Component>(scope).inject(this)
    }

    override fun initActionBar() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
