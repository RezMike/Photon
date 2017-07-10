package io.github.rezmike.foton.ui.others

import android.view.MenuItem

class MenuItemHolder(val itemTitle: String?,
                     val iconRes: Int,
                     val listener: (MenuItem) -> Boolean,
                     val showAsActionFlag: Int)