package io.github.rezmike.foton.ui.screens.navigation

import android.os.Bundle
import io.github.rezmike.foton.mortar.ScreenScoper
import io.github.rezmike.foton.ui.abstracts.AbstractPresenter
import mortar.MortarScope

class NavigationPresenter : AbstractPresenter<NavigationView, NavigationModel, NavigationPresenter>() {

    private var currentItem: Int = NavigationView.MAIN_SCREEN

    override fun initDagger(scope: MortarScope?) {
        ScreenScoper.getDaggerComponent<NavigationScreen.Component>(scope!!).inject(this)
    }

    override fun onLoad(savedInstanceState: Bundle?) {
        super.onLoad(savedInstanceState)
        view?.initView(currentItem)
    }

    fun onClickMain() {
        saveItemSelected(NavigationView.MAIN_SCREEN)
    }

    fun onClickLoad() {
        saveItemSelected(NavigationView.LOAD_SCREEN)
    }

    fun onClickProfile() {
        saveItemSelected(NavigationView.PROFILE_SCREEN)
    }

    private fun saveItemSelected(item: Int) {
        currentItem = item
        view?.turnScreen(item)
    }
}