package io.github.rezmike.foton.ui.abstracts

import android.util.Log
import io.github.rezmike.foton.utils.ScreenScoper

abstract class AbstractScreen<T> {

    fun getScopeName(): String = javaClass.name

    abstract fun createScreenComponent(parentComponent: T): Any

    fun unregisterScope() {
        Log.d("ScreenScoper", "unregisterScope: " + getScopeName())
        ScreenScoper.destroyScreenScope(getScopeName())
    }

    fun getLayoutResId(): Int {
        val screen = javaClass.getAnnotation(Screen::class.java)
        val layout = screen?.value ?: throw IllegalStateException("@Screen annotation is missing on screen " + getScopeName())
        return layout
    }
}
