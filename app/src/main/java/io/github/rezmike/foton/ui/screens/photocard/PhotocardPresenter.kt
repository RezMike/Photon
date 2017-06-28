package io.github.rezmike.foton.ui.screens.photocard

import android.os.Bundle
import android.view.MenuItem
import io.github.rezmike.foton.R
import io.github.rezmike.foton.data.storage.PhotoCardRealm
import io.github.rezmike.foton.ui.abstracts.AbstractPresenter
import io.github.rezmike.foton.ui.activities.root.MenuItemHolder
import io.github.rezmike.foton.utils.DaggerService
import mortar.MortarScope

class PhotocardPresenter(val photoCard: PhotoCardRealm) : AbstractPresenter<PhotocardView, PhotocardModel, PhotocardPresenter>() {

    override fun initDagger(scope: MortarScope) {
        DaggerService.getDaggerComponent<PhotocardScreen.Component>(scope).inject(this)
    }

    override fun initActionBar() {
        rootPresenter.ActionBarBuilder()
                .setTitle(view?.resources?.getString(R.string.photocard_title)!!)
                .setBackArrow(true)
                .setOverFlowIcon(R.drawable.ic_custom_menu_black_24dp)
                .addAction(MenuItemHolder(view?.context?.getString(R.string.photocard_menu_in_favorites), 0, { onClickInFavorite() }, MenuItem.SHOW_AS_ACTION_NEVER))
                .addAction(MenuItemHolder(view?.context?.getString(R.string.photocard_menu_share), 0, { onClickShare() }, MenuItem.SHOW_AS_ACTION_NEVER))
                .addAction(MenuItemHolder(view?.context?.getString(R.string.photocard_menu_save), 0, { onClickSave() }, MenuItem.SHOW_AS_ACTION_NEVER))
                .build()
    }

    override fun onLoad(savedInstanceState: Bundle?) {
        super.onLoad(savedInstanceState)
        view.showPhotoCardInfo(photoCard)
    }

    private fun onClickInFavorite(): Boolean {
        return true
    }

    private fun onClickShare(): Boolean {
        return true
    }

    private fun onClickSave(): Boolean {
        return true
    }
}
