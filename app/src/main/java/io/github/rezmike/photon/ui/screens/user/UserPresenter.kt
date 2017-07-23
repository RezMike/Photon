package io.github.rezmike.photon.ui.screens.user

import android.os.Bundle
import flow.Flow
import io.github.rezmike.photon.R
import io.github.rezmike.photon.data.storage.realm.AlbumRealm
import io.github.rezmike.photon.data.storage.realm.UserRealm
import io.github.rezmike.photon.ui.screens.AbstractPresenter
import io.github.rezmike.photon.ui.screens.album.AlbumScreen
import io.github.rezmike.photon.utils.DaggerService
import io.github.rezmike.photon.utils.toArrayList
import io.realm.RealmChangeListener
import mortar.MortarScope
import rx.android.schedulers.AndroidSchedulers

class UserPresenter(val userId: String) : AbstractPresenter<UserView, UserModel, UserPresenter>() {

    private var userRealm: UserRealm? = null
    private var listener: RealmChangeListener<UserRealm>? = null

    override fun initDagger(scope: MortarScope) {
        DaggerService.getDaggerComponent<UserScreen.Component>(scope).inject(this)
    }

    override fun initActionBar() {
        rootPresenter.ActionBarBuilder()
                .setBackArrow(true)
                .setTitle(view.context.resources.getString(R.string.user_title))
                .build()
    }

    override fun onLoad(savedInstanceState: Bundle?) {
        super.onLoad(savedInstanceState)
        loadUserData(userId)
    }

    fun loadUserData(userId: String){
        getRootView()?.showLoad()
        model.getUserDate(userId)
                .subscribeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate { getRootView()?.hideLoad() }
                .subscribe({
                    userRealm = it
                    initData()
                }, {
                    getRootView()?.showError(it)
                })
    }

    private fun initData() {
        view.showUserInfo(userRealm!!)
        listener = RealmChangeListener {
            view?.initUserData(it)
            view?.adapter?.reloadAdapter(it.albums.toArrayList())
        }
        userRealm?.addChangeListener(listener)
    }

    override fun dropView(view: UserView) {
        if (listener != null) userRealm?.removeChangeListener(listener)
        super.dropView(view)
    }

    fun onClickItem(albumRealm: AlbumRealm) {
        Flow.get(view).set(AlbumScreen(albumRealm))
    }
}
