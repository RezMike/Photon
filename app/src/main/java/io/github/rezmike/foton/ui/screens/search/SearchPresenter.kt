package io.github.rezmike.foton.ui.screens.search

import io.github.rezmike.foton.ui.abstracts.AbstractPresenter
import io.github.rezmike.foton.utils.DaggerService
import mortar.MortarScope

class SearchPresenter : AbstractPresenter<SearchView, SearchModel, SearchPresenter>() {

    override fun initDagger(scope: MortarScope) {
        DaggerService.getDaggerComponent<SearchScreen.Component>(scope).inject(this)
    }

    override fun initActionBar() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
