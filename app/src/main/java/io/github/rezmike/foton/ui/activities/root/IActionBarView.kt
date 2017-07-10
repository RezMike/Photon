package io.github.rezmike.foton.ui.activities.root

import io.github.rezmike.foton.ui.others.MenuItemHolder

interface IActionBarView {
    fun setTitleBar(title: CharSequence)
    fun setVisibleBar(enable: Boolean)
    fun setBackArrow(enable: Boolean)
    fun setOverFlowIcon(iconRes: Int?)
    fun setMenuItems(items: ArrayList<MenuItemHolder>)
}


