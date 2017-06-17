package io.github.rezmike.foton.ui.abstracts

import android.os.Bundle
import io.github.rezmike.foton.ui.root.RootActivity
import io.github.rezmike.foton.ui.root.RootPresenter
import mortar.MortarScope
import mortar.ViewPresenter
import javax.inject.Inject

abstract class AbstractPresenter<V : AbstractView<P, V>, M : AbstractModel, P : AbstractPresenter<V, M, P>> : ViewPresenter<V>() {

    @Inject
    protected lateinit var rootPresenter: RootPresenter
    @Inject
    protected lateinit var model: M

    override fun onEnterScope(scope: MortarScope) {
        super.onEnterScope(scope)
        initDagger(scope)
    }

    override fun onLoad(savedInstanceState: Bundle?) {
        super.onLoad(savedInstanceState)
        initActionBar()
    }

    abstract fun initDagger(scope: MortarScope)

    abstract fun initActionBar()

    fun getRootView(): RootActivity? {
        return rootPresenter.getRootView()
    }
}
