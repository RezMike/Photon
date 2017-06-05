package io.github.rezmike.foton.utils

import android.content.Context
import mortar.MortarScope
import java.util.*
import kotlin.reflect.KClass

object DaggerService {

    const val SERVICE_NAME = "MY_DAGGER_SERVICE"

    @JvmStatic
    private val sComponentMap = HashMap<KClass<*>, Any>()

    @JvmStatic
    @Suppress("unchecked_cast")
    fun <T> getDaggerComponent(context: Context): T = context.getSystemService(SERVICE_NAME) as T

    @JvmStatic
    fun <T> getDaggerComponent(scope: MortarScope): T = scope.getService(SERVICE_NAME)

    @JvmStatic
    fun registerComponent(componentClass: KClass<*>, daggerComponent: Any) {
        sComponentMap.put(componentClass, daggerComponent)
    }

    @JvmStatic
    @Suppress("unchecked_cast")
    fun <T : Any> getComponent(componentClass: KClass<T>): T? {
        val component = sComponentMap[componentClass]
        return component as T
    }
}
