package io.github.rezmike.foton.mortar

import android.content.Context
import flow.TreeKey
import io.github.rezmike.foton.ui.abstracts.AbstractScreen
import io.github.rezmike.foton.ui.root.RootActivity
import mortar.MortarScope
import java.util.*

object ScreenScoper {

    const val TAG = "ScreenScoper"


    val SERVICE_NAME = "DEPENDENCY_SERVICE"


    @Suppress("UNREACHABLE_CODE")
    @JvmStatic
    fun <T> getScreenScope(context: Context?, screen: AbstractScreen<T>): MortarScope? {

        val parentScope = MortarScope.getScope(context)

        val childScope = parentScope.findChild(screen.getScopeName())
        if (childScope != null) return childScope

        val parentComponent: T = parentScope.getService(SERVICE_NAME)
        val screenComponent: Any = screen.createScreenComponent(parentComponent)

        return parentScope.buildChild()
                .withService(ScreenScoper.SERVICE_NAME
                        , screenComponent)
                .build(screen.getScopeName())

    }

    @JvmStatic
    fun createScreenContext(rootContext: android.content.Context?, screen: AbstractScreen<*>): Context? {

        var context = rootContext
        val screens: ArrayList<AbstractScreen<*>> = ArrayList()

        var screenIn = screen
        while (screenIn is flow.TreeKey) {
            screens.add(screenIn)
            screenIn = screenIn.parentKey as AbstractScreen<*>
        }
        screens.add(screenIn)
        Collections.reverse(screens)
        for (newScreen in screens) {
            context = ScreenScoper.getScreenScope(context, newScreen)?.createContext(context) ?: throw NullPointerException("Mortar scope null")
        }
        return context
    }

    @JvmStatic
    fun destroyTearDownScope(context: android.content.Context?, screen: io.github.rezmike.foton.ui.abstracts.AbstractScreen<*>) {
        var screenIn = screen
        while (screenIn is flow.TreeKey) {
            screenIn = screenIn.parentKey as io.github.rezmike.foton.ui.abstracts.AbstractScreen<*>
        }
        mortar.MortarScope.getScope(context).findChild(screenIn.getScopeName()).destroy()
    }

    @JvmStatic
    @Suppress("unchecked_cast")
    fun <T> getDaggerComponent(context: Context): T = context.getSystemService(SERVICE_NAME) as T

    @JvmStatic
    fun <T> getDaggerComponent(scope: MortarScope): T = scope.getService(SERVICE_NAME)

}
