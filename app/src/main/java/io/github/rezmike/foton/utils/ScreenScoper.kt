package io.github.rezmike.foton.utils

import android.util.Log

import java.lang.reflect.ParameterizedType

import io.github.rezmike.foton.ui.abstracts.AbstractScreen
import mortar.MortarScope

object ScreenScoper {

    const val TAG = "ScreenScoper"

    @JvmStatic
    private val sScopeMap = HashMap<String, MortarScope>()

    @JvmStatic
    fun getScreenScope(screen: AbstractScreen<*>): MortarScope? {
        if (!sScopeMap.containsKey(screen.getScopeName())) {
            Log.d(TAG, "getScreenScope: create new scope")
            return createScreenScope(screen)
        } else {
            Log.d(TAG, "getScreenScope: return has scope")
            return sScopeMap[screen.getScopeName()]
        }
    }

    @JvmStatic
    fun registerScope(scope: MortarScope) {
        sScopeMap.put(scope.name, scope)
    }

    @JvmStatic
    fun destroyScreenScope(scopeName: String) {
        val mortarScope = sScopeMap[scopeName]
        mortarScope?.destroy()
        cleanScopeMap()
    }

    @JvmStatic
    private fun cleanScopeMap() {
        val iterator = sScopeMap.entries.iterator()
        while (iterator.hasNext()) {
            val entry = iterator.next()
            if (entry.value.isDestroyed) {
                iterator.remove()
            }
        }
    }

    @JvmStatic
    private fun getParentScopeName(screen: AbstractScreen<*>): String? {
        try {
            val genericName = ((screen.javaClass.genericSuperclass as ParameterizedType)
                    .actualTypeArguments[0] as Class<*>).name
            var parentScopeName = genericName
            if (parentScopeName.contains("$")) {
                parentScopeName = parentScopeName.substring(0, genericName.indexOf("$"))
            }
            return parentScopeName
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    @JvmStatic
    private fun <T> createScreenScope(screen: AbstractScreen<T>): MortarScope? {
        Log.d(TAG, "createScreenScope: with name : " + screen.getScopeName())
        val parentScope = sScopeMap[getParentScopeName(screen)]
        if (parentScope == null) return null;
        val screenComponent = screen.createScreenComponent(parentScope.getService(DaggerService.SERVICE_NAME))
        val newScope = parentScope.buildChild()
                .withService(DaggerService.SERVICE_NAME, screenComponent)
                .build(screen.getScopeName())
        registerScope(newScope)
        return newScope
    }
}
