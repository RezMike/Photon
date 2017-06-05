package io.github.rezmike.foton.utils

import android.content.Context
import mortar.MortarScope

object DaggerService {

    @JvmStatic
    @Suppress("unchecked_cast")
    fun <T> getDaggerComponent(context: Context): T = context.getSystemService(ScreenScoper.SERVICE_NAME) as T

    @JvmStatic
    fun <T> getDaggerComponent(scope: MortarScope): T = scope.getService(ScreenScoper.SERVICE_NAME)
}