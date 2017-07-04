package io.github.rezmike.foton.ui.screens.main

import android.os.Bundle
import android.view.MenuItem
import flow.Flow
import io.github.rezmike.foton.R
import io.github.rezmike.foton.data.storage.PhotoCardRealm
import io.github.rezmike.foton.ui.abstracts.AbstractPresenter
import io.github.rezmike.foton.ui.activities.root.MenuItemHolder
import io.github.rezmike.foton.ui.screens.photocard.PhotocardScreen
import io.github.rezmike.foton.ui.screens.register.RegisterScreen
import io.github.rezmike.foton.ui.screens.search.SearchScreen
import io.github.rezmike.foton.utils.DaggerService
import mortar.MortarScope

class MainPresenter : AbstractPresenter<MainView, MainModel, MainPresenter>() {

    override fun initDagger(scope: MortarScope) {
        DaggerService.getDaggerComponent<MainScreen.Component>(scope).inject(this)
    }

    override fun initActionBar() {
        val actionBar = rootPresenter.ActionBarBuilder()
                .setTitle(view?.resources?.getString(R.string.main_title)!!)
                .setOverFlowIcon(R.drawable.ic_custom_gear_black_24dp)
                .addAction(MenuItemHolder(view?.context?.getString(R.string.main_menu_search), R.drawable.ic_custom_search_black_24dp, { onClickSearch() }, MenuItem.SHOW_AS_ACTION_ALWAYS))
        if (model.isUserAuth()) {
            actionBar.addAction(MenuItemHolder(view?.context?.getString(R.string.main_menu_logout), 0, { onClickLogout() }, MenuItem.SHOW_AS_ACTION_NEVER))
        } else {
            actionBar.addAction(MenuItemHolder(view?.context?.getString(R.string.main_menu_login), 0, { onClickLogin() }, MenuItem.SHOW_AS_ACTION_NEVER))
                    .addAction(MenuItemHolder(view?.context?.getString(R.string.main_menu_register), 0, { onClickRegister() }, MenuItem.SHOW_AS_ACTION_NEVER))
        }
        actionBar.addAction(MenuItemHolder(view?.context?.getString(R.string.main_menu_connect_developers), 0, { onClickConnectDevelopers() }, MenuItem.SHOW_AS_ACTION_NEVER))
        actionBar.build()
    }

    override fun onLoad(savedInstanceState: Bundle?) {
        super.onLoad(savedInstanceState)
        model.getPhotoCardObs()
                .subscribe({ view.addItem(it) }, { getRootView()?.showError(it) })
    }

    fun onClickSearch(): Boolean {
        Flow.get(view).set(SearchScreen())
        return true
    }

    fun onClickLogout(): Boolean {
        model.logoutUser()
        initActionBar()
        return true
    }

    fun onClickLogin(): Boolean {
        rootPresenter.showLoginDialog()
        return true
    }

    fun onClickRegister(): Boolean {
        Flow.get(view).set(RegisterScreen())
        return true
    }

    fun onClickConnectDevelopers(): Boolean {
        return true
    }

    fun onClickItem(photoCardRealm: PhotoCardRealm) {
        Flow.get(view).set(PhotocardScreen(photoCardRealm))
    }
}
