package io.github.rezmike.foton.ui.abstracts

import mortar.MortarScope
import mortar.ViewPresenter
import javax.inject.Inject

abstract class AbstractPresenter<V : AbstractView<P, V>, M : AbstractModel, P : AbstractPresenter<V, M, P>> : ViewPresenter<V>() {

    @Inject
    protected lateinit var model: M

    override fun onEnterScope(scope: MortarScope) {
        super.onEnterScope(scope)
        initDagger(scope)
    }

    abstract fun initDagger(scope: MortarScope)
}