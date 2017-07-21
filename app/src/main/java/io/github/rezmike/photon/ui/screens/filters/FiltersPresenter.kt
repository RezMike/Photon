package io.github.rezmike.photon.ui.screens.filters

import io.github.rezmike.photon.ui.screens.AbstractPresenter
import io.github.rezmike.photon.utils.DaggerService
import mortar.MortarScope

class FiltersPresenter : AbstractPresenter<FiltersView, FiltersModel, FiltersPresenter>() {

    override fun initDagger(scope: MortarScope) {
        DaggerService.getDaggerComponent<FiltersScreen.Component>(scope).inject(this)
    }

    override fun initActionBar() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
