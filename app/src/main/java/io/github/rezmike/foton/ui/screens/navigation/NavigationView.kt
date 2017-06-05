package io.github.rezmike.foton.ui.screens.navigation

import kotlinx.android.synthetic.main.screen_navigation.*
import android.content.Context
import android.util.AttributeSet
import io.github.rezmike.foton.R
import io.github.rezmike.foton.ui.abstracts.AbstractView
import kotlinx.android.synthetic.main.screen_navigation.view.*
import io.github.rezmike.foton.ui.abstracts.AbstractScreen
import android.view.LayoutInflater
import android.view.View
import io.github.rezmike.foton.mortar.ScreenScoper
import io.github.rezmike.foton.ui.screens.profile.ProfileScreen
import io.github.rezmike.foton.ui.screens.splash.SplashScreen


class NavigationView(context: Context, attrs: AttributeSet)
    : AbstractView<NavigationPresenter, NavigationView>(context, attrs) {

    companion object {
        val MAIN_SCREEN = 0
        val PROFILE_SCREEN = 1
        val LOAD_SCREEN = 2
    }

    override fun initDagger(context: Context?) {
        ScreenScoper.getDaggerComponent<NavigationScreen.Component>(context!!).inject(this)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        navigation.setOnNavigationItemSelectedListener { item ->

            when (item.itemId) {
                R.id.navigation_main -> {
                    presenter.onClickMain()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_profile -> {
                    presenter.onClickProfile()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_load -> {
                    presenter.onClickLoad()
                    return@setOnNavigationItemSelectedListener true
                }
                else -> return@setOnNavigationItemSelectedListener false
            }
        }
    }

    //region ======================== INavigationView ========================
    fun initView(position: Int) {
        turnScreen(position)
        navigation.selectedItemId = position
    }
    //endregion

    fun turnScreen(position: Int) {

        val screen = getScreenInfo(position).second

        val screenScope = ScreenScoper.getScreenScope(container.context, screen)
        val screenContext = screenScope?.createContext(container.context)
        val newView: View

        container.removeAllViews()
        newView = LayoutInflater.from(screenContext).inflate(screen.getLayout(), container, false)
        container.addView(newView)


    }

    private fun getScreenInfo(position: Int): Pair<Int, AbstractScreen<*>> {
        var screen: AbstractScreen<*>? = null
        var itemId: Int = MAIN_SCREEN
        when (position) {
            MAIN_SCREEN -> {
                //TODO сюда нужно вставлять скрины
                screen = SplashScreen()
                itemId = R.id.navigation_main
            }
            PROFILE_SCREEN -> {

                screen = ProfileScreen()//TODO сюда нужно вставлять скрины
                itemId = R.id.navigation_profile
            }
            LOAD_SCREEN -> {
                //TODO сюда нужно вставлять скрины
                screen = SplashScreen()
                itemId = R.id.navigation_load
            }
        }
        return Pair(itemId, screen!!)
    }


}