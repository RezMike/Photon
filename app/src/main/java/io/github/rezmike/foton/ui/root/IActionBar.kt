package io.github.rezmike.foton.ui.root

import android.support.v4.view.ViewPager



interface IActionBar {
    fun setTitleBar(title: CharSequence)
    fun setVisibleBar(enable: Boolean)
    fun setBackArrow(enable: Boolean)
    fun setMenuItem(items: ArrayList<MenuItemHolder>)
    fun setTabLayout(pager: ViewPager)
    fun removeTabLayout()
}


