package io.github.rezmike.photon.ui.screens.upload

import android.content.Context
import android.util.AttributeSet
import io.github.rezmike.photon.ui.screens.AbstractView
import io.github.rezmike.photon.utils.DaggerService
import kotlinx.android.synthetic.main.screen_upload.view.*

class UploadView(context: Context, attrs: AttributeSet?) : AbstractView<UploadPresenter, UploadView>(context, attrs) {

    override fun initDagger(context: Context) {
        DaggerService.getDaggerComponent<UploadScreen.Component>(context).inject(this)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        choose_photo_btn.setOnClickListener { presenter.clickOnChoosePhoto() }
    }

    override fun onBackPressed(): Boolean {
        return false
    }
}
