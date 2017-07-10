package io.github.rezmike.photon.ui.abstracts

import io.github.rezmike.photon.ui.activities.root.BottomBarItems

abstract class AbstractScreen<in T>(val currentBottomItem: BottomBarItems) {

    fun getScopeName(): String = this::class.java.name

    abstract fun createScreenComponent(parentComponent: T): Any

    abstract fun getLayoutResId(): Int

    fun getLayout(): Int = getLayoutResId()
}
