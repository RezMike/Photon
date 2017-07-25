package io.github.rezmike.photon.ui.screens.selection

import io.github.rezmike.photon.ui.activities.root.AccountModel
import io.github.rezmike.photon.ui.screens.AbstractPresenter
import io.github.rezmike.photon.utils.DaggerService
import mortar.MortarScope

class SelectionPresenter : AbstractPresenter<SelectionView, AccountModel, SelectionPresenter>() {

    override fun initDagger(scope: MortarScope) {
        DaggerService.getDaggerComponent<SelectionScreen.Component>(scope).inject(this)
    }

    override fun initActionBar() {
        rootPresenter.ActionBarBuilder()
                .setVisible(false)
                .setTab(view.getPager())
                .build()
    }
}