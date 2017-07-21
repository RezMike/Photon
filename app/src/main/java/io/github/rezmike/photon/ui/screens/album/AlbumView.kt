package io.github.rezmike.photon.ui.screens.album

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.util.AttributeSet
import android.view.View
import com.squareup.picasso.Picasso
import io.github.rezmike.photon.data.storage.realm.AlbumRealm
import io.github.rezmike.photon.ui.screens.AbstractView
import io.github.rezmike.photon.utils.DaggerService
import io.github.rezmike.photon.utils.toArrayList
import kotlinx.android.synthetic.main.screen_album.view.*
import javax.inject.Inject

class AlbumView(context: Context, attrs: AttributeSet?) : AbstractView<AlbumPresenter, AlbumView>(context, attrs) {

    @Inject
    lateinit var picasso: Picasso

    val adapter = AlbumPhotoAdapter(picasso, {})

    override fun initDagger(context: Context) {
        DaggerService.getDaggerComponent<AlbumScreen.Component>(context).inject(this)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        list_photocard.layoutManager = GridLayoutManager(context, 3)
        list_photocard.adapter = adapter
    }

    override fun onBackPressed(): Boolean {
        return false
    }

    fun showAlbumInfo(album: AlbumRealm) {
        album_title_tv.text = album.title
        album_description_tv.text = album.description
        photocard_count.text = album.photoCards.size.toString()
    }

    fun showList(album: AlbumRealm) {
        list_photocard.visibility = View.VISIBLE
        album_is_empty_tv.visibility = View.GONE
        adapter.reloadAdapter(album.photoCards.toArrayList())
    }

    fun showListEmpty() {
        list_photocard.visibility = View.GONE
        album_is_empty_tv.visibility = View.VISIBLE
    }
}