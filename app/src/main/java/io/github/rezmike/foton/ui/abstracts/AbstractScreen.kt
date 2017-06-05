package io.github.rezmike.foton.ui.abstracts


abstract class AbstractScreen<in T> {

    fun getScopeName(): String = javaClass.name

    abstract fun createScreenComponent(parentComponent: T): Any

    fun getLayoutResId(): Int {

        val layout = setLayoutResId() ?: throw IllegalStateException("Method setLayoutResId must is not null  " + getScopeName())
        return layout
    }

    abstract fun setLayoutResId(): Int?
}
