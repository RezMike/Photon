package io.github.rezmike.foton.ui.screens.upload

import android.content.Context
import android.util.AttributeSet
import flow.Flow
import io.github.rezmike.foton.ui.abstracts.AbstractView
import io.github.rezmike.foton.ui.screens.add_info.AddInfoScreen
import io.github.rezmike.foton.utils.DaggerService
import kotlinx.android.synthetic.main.screen_upload.view.*

class UploadView(context: Context, attrs: AttributeSet?) : AbstractView<UploadPresenter, UploadView>(context, attrs) {

    override fun initDagger(context: Context) {
        DaggerService.getDaggerComponent<UploadScreen.Component>(context).inject(this)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        choose_photo_btn.setOnClickListener { presenter.clickOnChoosePhoto() }
    }

    fun showAddInfoScreen(photoUri: String) {
        Flow.get(this).set(AddInfoScreen(photoUri))
    }

    override fun onBackPressed(): Boolean {
        return false
    }
}
