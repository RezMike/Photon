package io.github.rezmike.foton.ui.screens.main

import io.github.rezmike.foton.ui.abstracts.AbstractPresenter
import io.github.rezmike.foton.utils.DaggerService
import mortar.MortarScope

class MainPresenter : AbstractPresenter<MainView, MainModel, MainPresenter>() {

    override fun initDagger(scope: MortarScope) {
        DaggerService.getDaggerComponent<MainScreen.Component>(scope).inject(this)
    }

    override fun initActionBar() {
        rootPresenter.ActionBarBuilder()
                .setTitle("Фотон")
                .build()
    }
}
