package io.github.rezmike.photon.ui.others

import android.view.MenuItem

class MenuItemHolder(val itemTitle: String?,
                     val iconRes: Int,
                     val listener: (MenuItem) -> Boolean,
                     val showAsActionFlag: Int)