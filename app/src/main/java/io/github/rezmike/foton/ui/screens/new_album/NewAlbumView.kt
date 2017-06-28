package io.github.rezmike.foton.ui.screens.new_album

import android.content.Context
import android.util.AttributeSet
import io.github.rezmike.foton.ui.abstracts.AbstractView
import io.github.rezmike.foton.utils.DaggerService

class NewAlbumView(context: Context, attrs: AttributeSet?) : AbstractView<NewAlbumPresenter, NewAlbumView>(context, attrs) {

    override fun initDagger(context: Context) {
        DaggerService.getDaggerComponent<NewAlbumScreen.Component>(context).inject(this)
    }
}
