package io.github.rezmike.photon.ui.screens.add_info

import io.github.rezmike.photon.ui.screens.AbstractPresenter
import io.github.rezmike.photon.utils.DaggerService
import mortar.MortarScope

class AddInfoPresenter : AbstractPresenter<AddInfoView, AddInfoModel, AddInfoPresenter>() {

    override fun initDagger(scope: MortarScope) {
        DaggerService.getDaggerComponent<AddInfoScreen.Component>(scope).inject(this)
    }

    override fun initActionBar() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
