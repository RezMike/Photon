package io.github.rezmike.foton.ui.abstracts

import android.os.Bundle
import io.github.rezmike.foton.ui.activities.root.RootActivity
import io.github.rezmike.foton.ui.activities.root.RootPresenter
import mortar.MortarScope
import mortar.ViewPresenter
import rx.Observable
import rx.Subscriber
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription
import javax.inject.Inject

abstract class AbstractPresenter<V : AbstractView<P, V>, M : AbstractModel, P : AbstractPresenter<V, M, P>> : ViewPresenter<V>() {

    @Inject
    protected lateinit var rootPresenter: RootPresenter
    @Inject
    protected lateinit var model: M

    protected lateinit var compSubs: CompositeSubscription

    override fun onEnterScope(scope: MortarScope) {
        super.onEnterScope(scope)
        initDagger(scope)
    }

    override fun onLoad(savedInstanceState: Bundle?) {
        super.onLoad(savedInstanceState)
        compSubs = CompositeSubscription()
        initActionBar()
    }

    override fun dropView(view: V) {
        if (compSubs.hasSubscriptions()) compSubs.unsubscribe()
        super.dropView(view)
    }

    abstract fun initDagger(scope: MortarScope)

    abstract fun initActionBar()

    fun getRootView(): RootActivity? {
        return rootPresenter.getRootView()
    }

    protected abstract inner class ViewSubscriber<T> : Subscriber<T>() {
        override fun onCompleted() {
            getRootView()?.hideLoad()
        }

        override fun onError(e: Throwable) {
            getRootView()?.hideLoad()
            getRootView()?.showError(e)
        }
    }

    protected fun <T> subscribe(observable: Observable<T>, subscriber: ViewSubscriber<T>): Subscription {
        return observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber)
    }
}