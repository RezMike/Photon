package io.github.rezmike.photon.ui.screens.selection

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.rezmike.photon.R
import io.github.rezmike.photon.ui.screens.selection.filters.FiltersScreen
import io.github.rezmike.photon.ui.screens.selection.search.SearchScreen
import io.github.rezmike.photon.utils.ScreenScoper

class SelectionAdapter(val context: Context) : PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val screen = when (position) {
            0 -> SearchScreen()
            1 -> FiltersScreen()
            else -> throw IllegalStateException("Selection screen must contain only 2 pages")
        }

        val screenScope = ScreenScoper.createScreenScopeFromContext(container.context, screen)
        val screenContext = screenScope.createContext(container.context)
        val newView = LayoutInflater.from(screenContext).inflate(screen.getLayoutResId(), container, false)
        container.addView(newView)

        return newView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any?) {
        container.removeView(`object` as View)
    }

    override fun getPageTitle(position: Int): String = when (position) {
        0 -> context.getString(R.string.search_title)
        1 -> context.getString(R.string.filters_title)
        else -> throw IllegalStateException("Selection screen must contain only 2 pages")
    }

    override fun getCount() = 2

    override fun isViewFromObject(view: View, `object`: Any) = view == `object`
}