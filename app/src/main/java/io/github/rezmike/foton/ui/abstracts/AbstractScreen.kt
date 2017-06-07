package io.github.rezmike.foton.ui.abstracts

abstract class AbstractScreen<in T> {

    fun getScopeName(): String = javaClass.name

    abstract fun createScreenComponent(parentComponent: T): Any

    abstract fun getLayoutResId(): Int

    fun getLayout() : Int = getLayoutResId()
}
