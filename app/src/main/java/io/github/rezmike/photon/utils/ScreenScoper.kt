package io.github.rezmike.photon.utils

import android.content.Context
import flow.TreeKey
import io.github.rezmike.photon.ui.screens.AbstractScreen
import mortar.MortarScope
import java.util.*
import kotlin.collections.ArrayList

object ScreenScoper {

    @JvmStatic
    fun <T> getScreenScope(context: Context, screen: AbstractScreen<T>): MortarScope {

        val parentScope = MortarScope.getScope(context)

        val childScope = parentScope.findChild(screen.getScopeName())
        if (childScope != null) return childScope

        val parentComponent: T = parentScope.getService(DaggerService.SERVICE_NAME)
        val screenComponent: Any = screen.createScreenComponent(parentComponent)

        return parentScope.buildChild()
                .withService(DaggerService.SERVICE_NAME, screenComponent)
                .build(screen.getScopeName())
    }

    @JvmStatic
    fun createScreenContext(rootContext: Context, screen: AbstractScreen<*>): Context {

        var context = rootContext
        val screens: ArrayList<AbstractScreen<*>> = ArrayList()

        var screenIn = screen
        while (screenIn is TreeKey) {
            screens.add(screenIn)
            screenIn = screenIn.parentKey as AbstractScreen<*>
        }
        screens.add(screenIn)
        Collections.reverse(screens)
        for (newScreen in screens) {
            context = getScreenScope(context, newScreen).createContext(context)
        }
        return context
    }

    @JvmStatic
    fun destroyTearDownScope(context: Context, screen: AbstractScreen<*>) {
        var screenIn = screen
        while (screenIn is TreeKey) {
            screenIn = screenIn.parentKey as AbstractScreen<*>
        }
        MortarScope.getScope(context).findChild(screenIn.getScopeName()).destroy()
    }
}
