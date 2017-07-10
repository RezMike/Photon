package io.github.rezmike.foton.ui.screens.album

import android.content.Context
import android.util.AttributeSet
import io.github.rezmike.foton.ui.abstracts.AbstractView
import io.github.rezmike.foton.utils.DaggerService

class AlbumView(context: Context, attrs: AttributeSet?) : AbstractView<AlbumPresenter, AlbumView>(context, attrs) {

    override fun initDagger(context: Context) {
        DaggerService.getDaggerComponent<AlbumScreen.Component>(context).inject(this)
    }

    override fun onBackPressed(): Boolean {
        return false
    }
}
