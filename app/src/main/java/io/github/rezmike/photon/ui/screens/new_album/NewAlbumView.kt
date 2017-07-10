package io.github.rezmike.photon.ui.screens.new_album

import android.content.Context
import android.util.AttributeSet
import io.github.rezmike.photon.ui.abstracts.AbstractView
import io.github.rezmike.photon.utils.DaggerService

class NewAlbumView(context: Context, attrs: AttributeSet?) : AbstractView<NewAlbumPresenter, NewAlbumView>(context, attrs) {

    override fun initDagger(context: Context) {
        DaggerService.getDaggerComponent<NewAlbumScreen.Component>(context).inject(this)
    }

    override fun onBackPressed(): Boolean {
        return false
    }
}
