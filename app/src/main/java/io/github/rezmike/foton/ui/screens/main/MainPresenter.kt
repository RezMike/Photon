package io.github.rezmike.foton.ui.screens.main

import android.os.Bundle
import android.view.MenuItem
import io.github.rezmike.foton.R
import io.github.rezmike.foton.ui.abstracts.AbstractPresenter
import io.github.rezmike.foton.ui.activities.root.MenuItemHolder
import io.github.rezmike.foton.utils.DaggerService
import mortar.MortarScope


class MainPresenter : AbstractPresenter<MainView, MainModel, MainPresenter>() {

    override fun initDagger(scope: MortarScope) {
        DaggerService.getDaggerComponent<MainScreen.Component>(scope).inject(this)
    }

    override fun initActionBar() {
        val actionBar = rootPresenter.newActionBarBuilder()
                .setTitle(view?.resources?.getString(R.string.main_search) as CharSequence)
                .setOverFlowIcon(R.drawable.ic_custom_gear_black_24dp)
                .addAction(MenuItemHolder(view?.resources?.getString(R.string.main_search) as CharSequence, R.drawable.ic_custom_search_black_24dp,
                        MenuItem.OnMenuItemClickListener { true }, MenuItem.SHOW_AS_ACTION_ALWAYS))
        if (model.isAuth()) {
            //TODO  сделать если зарегистрированный пользователь
        } else {
            actionBar.addAction(MenuItemHolder(view?.resources?.getString(R.string.main_enter) as CharSequence, 0,
                    MenuItem.OnMenuItemClickListener { onClickEnter() }, MenuItem.SHOW_AS_ACTION_NEVER))

                    .addAction(MenuItemHolder(view?.resources?.getString(R.string.main_sign_up) as CharSequence, 0,
                            MenuItem.OnMenuItemClickListener { onClickSignUp() }, MenuItem.SHOW_AS_ACTION_NEVER))

                    .addAction(MenuItemHolder(view?.resources?.getString(R.string.main_connect_developer) as CharSequence, 0,
                            MenuItem.OnMenuItemClickListener { onClickConnectDeveloper() }, MenuItem.SHOW_AS_ACTION_NEVER))
        }
        actionBar.build()
    }

    override fun onLoad(savedInstanceState: Bundle?) {
        super.onLoad(savedInstanceState)

        model.getPhotoCardObs()
                .subscribe({ view.addItem(it) }, { getRootView()?.showError(it) })

    }

    fun onClickEnter(): Boolean {
        return true
    }

    fun onClickSignUp(): Boolean {
        return true
    }

    fun onClickConnectDeveloper(): Boolean {
        return true
    }
}
