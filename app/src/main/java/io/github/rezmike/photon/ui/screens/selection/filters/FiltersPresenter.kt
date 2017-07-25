package io.github.rezmike.photon.ui.screens.selection.filters

import io.github.rezmike.photon.ui.screens.AbstractPresenter
import io.github.rezmike.photon.utils.DaggerService
import mortar.MortarScope

class FiltersPresenter : AbstractPresenter<FiltersView, FiltersModel, FiltersPresenter>() {

    override fun initDagger(scope: MortarScope) {
        DaggerService.getDaggerComponent<FiltersScreen.Component>(scope).inject(this)
    }

    override fun initActionBar() {
        // do nothing
    }
}
