package io.github.rezmike.foton.ui.root

import io.github.rezmike.foton.utils.DaggerService
import mortar.MortarScope
import mortar.Presenter
import mortar.bundler.BundleService

class RootPresenter : Presenter<RootActivity>() {

    var currentItem: Int = RootActivity.MAIN_SCREEN

    override fun onEnterScope(scope: MortarScope) {
        super.onEnterScope(scope)
        DaggerService.getDaggerComponent<RootActivity.RootComponent>(scope).inject(this)
    }

    override fun extractBundleService(view: RootActivity): BundleService {
        return BundleService.getBundleService(view)
    }

    fun onClickMain() {
        saveItemSelected(RootActivity.MAIN_SCREEN)
    }

    fun onClickProfile() {
        saveItemSelected(RootActivity.PROFILE_SCREEN)
    }

    fun onClickLoad() {
        saveItemSelected(RootActivity.LOAD_SCREEN)
    }

    private fun saveItemSelected(item: Int) {
        if (currentItem != item) {
            currentItem = item
            view?.turnScreen(item)
        }
    }
}