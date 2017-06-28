package io.github.rezmike.foton.ui.activities.root

import android.support.v4.view.ViewPager

interface IActionBarView {
    fun setTitleBar(title: CharSequence)
    fun setVisibleBar(enable: Boolean)
    fun setBackArrow(enable: Boolean)
    fun setOverFlowIcon(iconRes: Int?)
    fun setMenuItem(items: List<MenuItemHolder>)
    fun setTabLayout(pager: ViewPager)
    fun removeTabLayout()
}


