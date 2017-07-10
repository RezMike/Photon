package io.github.rezmike.photon.utils

import android.content.Context
import mortar.MortarScope

object DaggerService {

    const val SERVICE_NAME = "DEPENDENCY_SERVICE"

    @JvmStatic
    @Suppress("unchecked_cast")
    fun <T> getDaggerComponent(context: Context): T = context.getSystemService(SERVICE_NAME) as T

    @JvmStatic
    fun <T> getDaggerComponent(scope: MortarScope): T = scope.getService(SERVICE_NAME)
}