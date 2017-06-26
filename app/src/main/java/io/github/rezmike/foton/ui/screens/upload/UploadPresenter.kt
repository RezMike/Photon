package io.github.rezmike.foton.ui.screens.upload

import io.github.rezmike.foton.ui.abstracts.AbstractPresenter
import io.github.rezmike.foton.utils.DaggerService
import mortar.MortarScope

class UploadPresenter : AbstractPresenter<UploadView, UploadModel, UploadPresenter>() {

    override fun initDagger(scope: MortarScope) {
        DaggerService.getDaggerComponent<UploadScreen.Component>(scope).inject(this)
    }

    override fun initActionBar() {
        rootPresenter.ActionBarBuilder()
                .setVisible(false)
                .build()
    }
}
