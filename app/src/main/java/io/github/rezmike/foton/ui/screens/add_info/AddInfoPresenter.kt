package io.github.rezmike.foton.ui.screens.add_info

import io.github.rezmike.foton.ui.abstracts.AbstractPresenter
import io.github.rezmike.foton.utils.DaggerService
import mortar.MortarScope

class AddInfoPresenter : AbstractPresenter<AddInfoView, AddInfoModel, AddInfoPresenter>() {

    override fun initDagger(scope: MortarScope) {
        DaggerService.getDaggerComponent<AddInfoScreen.Component>(scope).inject(this)
    }

    override fun initActionBar() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
