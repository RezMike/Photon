package io.github.rezmike.photon.ui.screens.user

import flow.Flow
import io.github.rezmike.photon.R
import io.github.rezmike.photon.data.storage.realm.AlbumRealm
import io.github.rezmike.photon.ui.activities.root.BottomBarItems
import io.github.rezmike.photon.ui.screens.AbstractPresenter
import io.github.rezmike.photon.ui.screens.album.AlbumScreen
import io.github.rezmike.photon.utils.DaggerService
import mortar.MortarScope

class UserPresenter(val userId: String, val bottomBarItem: BottomBarItems) : AbstractPresenter<UserView, UserModel, UserPresenter>() {

    override fun initDagger(scope: MortarScope) {
        DaggerService.getDaggerComponent<UserScreen.Component>(scope).inject(this)
    }

    override fun initActionBar() {
        rootPresenter.ActionBarBuilder()
                .setBackArrow(true)
                .setTitle(view.context.resources.getString(R.string.user_title))
                .build()
    }

    fun onClickItem(albumRealm: AlbumRealm) {
        Flow.get(view).set(AlbumScreen(albumRealm))
    }
}
