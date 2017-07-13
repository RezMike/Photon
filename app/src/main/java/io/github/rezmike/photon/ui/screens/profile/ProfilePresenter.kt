package io.github.rezmike.photon.ui.screens.profile

import android.os.Bundle
import android.view.MenuItem
import flow.Flow
import io.github.rezmike.photon.R
import io.github.rezmike.photon.data.storage.realm.AlbumRealm
import io.github.rezmike.photon.ui.abstracts.AbstractPresenter
import io.github.rezmike.photon.ui.activities.root.BottomBarItems
import io.github.rezmike.photon.ui.others.MenuItemHolder
import io.github.rezmike.photon.ui.screens.album.AlbumScreen
import io.github.rezmike.photon.utils.DaggerService
import mortar.MortarScope
import rx.android.schedulers.AndroidSchedulers

class ProfilePresenter : AbstractPresenter<ProfileView, ProfileModel, ProfilePresenter>() {

    override fun initDagger(scope: MortarScope) {
        DaggerService.getDaggerComponent<ProfileScreen.Component>(scope).inject(this)
    }

    override fun initActionBar() {
        rootPresenter.ActionBarBuilder()
                .setTitle(view?.context?.getString(R.string.profile_title)!!)
                .setOverFlowIcon(R.drawable.ic_custom_menu_black_24dp)
                .addAction(MenuItemHolder(view?.context?.getString(R.string.profile_menu_create_album), 0, { onClickNewAlbum() }, MenuItem.SHOW_AS_ACTION_NEVER))
                .addAction(MenuItemHolder(view?.context?.getString(R.string.profile_menu_edit_profile), 0, { onClickEditProfile() }, MenuItem.SHOW_AS_ACTION_NEVER))
                .addAction(MenuItemHolder(view?.context?.getString(R.string.profile_menu_change_avatar), 0, { onClickChangeAvatar() }, MenuItem.SHOW_AS_ACTION_NEVER))
                .addAction(MenuItemHolder(view?.context?.getString(R.string.profile_menu_logout), 0, { onClickLogout() }, MenuItem.SHOW_AS_ACTION_NEVER))
                .build()
    }

    override fun onLoad(savedInstanceState: Bundle?) {
        super.onLoad(savedInstanceState)
        loadProfileInfo()
    }

    private fun loadProfileInfo() {
        getRootView()?.showProgress()
        model.getProfileData()
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate { getRootView()?.hideProgress() }
                .subscribe({ view?.showProfileInfo(it) }, { getRootView()?.showError(it) })
    }

    fun onClickNewAlbum(): Boolean {
        rootPresenter.showAlbumDialog()
        return true
    }

    fun onClickEditProfile(): Boolean {
        TODO("not implemented")
    }

    fun onClickChangeAvatar(): Boolean {
        TODO("not implemented")
    }

    fun onClickLogout(): Boolean {
        model.logoutUser()
        rootPresenter.onClickProfile()
        return true
    }

    fun onClickItem(album: AlbumRealm) {
        Flow.get(view).set(AlbumScreen(album, BottomBarItems.PROFILE))
    }
}