package io.github.rezmike.foton.ui.activities.root

interface IActionBarView {
    fun setTitleBar(title: CharSequence)
    fun setVisibleBar(enable: Boolean)
    fun setBackArrow(enable: Boolean)
    fun setOverFlowIcon(iconRes: Int?)
    fun setMenuItems(items: ArrayList<MenuItemHolder>)
}


