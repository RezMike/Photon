package io.github.rezmike.foton.ui.activities.root

import android.view.MenuItem

class MenuItemHolder(val itemTitle: String?,
                     val iconRes: Int,
                     val listener: (MenuItem) -> Boolean,
                     val showAsActionFlag: Int)