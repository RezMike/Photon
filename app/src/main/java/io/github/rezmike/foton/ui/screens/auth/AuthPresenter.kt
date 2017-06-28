package io.github.rezmike.foton.ui.screens.auth

import android.view.MenuItem
import flow.Flow
import io.github.rezmike.foton.R
import io.github.rezmike.foton.ui.abstracts.AbstractPresenter
import io.github.rezmike.foton.ui.activities.root.MenuItemHolder
import io.github.rezmike.foton.ui.screens.login.LoginScreen
import io.github.rezmike.foton.ui.screens.register.RegisterScreen
import io.github.rezmike.foton.utils.DaggerService
import mortar.MortarScope

class AuthPresenter : AbstractPresenter<AuthView, AuthModel, AuthPresenter>() {

    override fun initDagger(scope: MortarScope) {
        DaggerService.getDaggerComponent<AuthScreen.Component>(scope).inject(this)
    }

    override fun initActionBar() {
        rootPresenter.ActionBarBuilder()
                .setTitle(view?.resources?.getString(R.string.auth_title)!!)
                .setOverFlowIcon(R.drawable.ic_custom_menu_black_24dp)
                .addAction(MenuItemHolder(view?.context?.getString(R.string.auth_menu_login), 0, { onClickLogin() }, MenuItem.SHOW_AS_ACTION_NEVER))
                .addAction(MenuItemHolder(view?.context?.getString(R.string.auth_menu_register), 0, { onClickRegister() }, MenuItem.SHOW_AS_ACTION_NEVER))
                .build()
    }

    fun onClickLogin(): Boolean {
        Flow.get(view).set(LoginScreen())
        return true
    }

    fun onClickRegister(): Boolean {
        Flow.get(view).set(RegisterScreen())
        return true
    }
}
