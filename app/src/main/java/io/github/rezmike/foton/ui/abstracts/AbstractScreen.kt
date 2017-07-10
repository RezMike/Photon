package io.github.rezmike.foton.ui.abstracts

import io.github.rezmike.foton.ui.activities.root.BottomBarItems

abstract class AbstractScreen<in T> {

    fun getScopeName(): String = javaClass.name

    abstract fun createScreenComponent(parentComponent: T): Any

    abstract fun getLayoutResId(): Int

    abstract fun getCurrentBottomItem(): BottomBarItems

    fun getLayout(): Int = getLayoutResId()
}
