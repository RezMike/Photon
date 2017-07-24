package io.github.rezmike.photon.ui.screens.add_info

import android.content.Context
import android.support.v4.view.ViewCompat
import android.support.v7.widget.GridLayoutManager
import android.util.AttributeSet
import com.squareup.picasso.Picasso
import io.github.rezmike.photon.data.storage.realm.AlbumRealm
import io.github.rezmike.photon.ui.screens.AbstractView
import io.github.rezmike.photon.utils.DaggerService
import kotlinx.android.synthetic.main.screen_add_info.view.*
import javax.inject.Inject

class AddInfoView(context: Context, attrs: AttributeSet?) : AbstractView<AddInfoPresenter, AddInfoView>(context, attrs) {

    @Inject
    lateinit var picasso: Picasso

    val adapter = AddInfoAlbumAdapter(picasso, { presenter.onClickItem(it) })

    override fun initDagger(context: Context) {
        DaggerService.getDaggerComponent<AddInfoScreen.Component>(context).inject(this)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        ViewCompat.setNestedScrollingEnabled(list_albums, false)

        list_albums.layoutManager = GridLayoutManager(context, 2)
        list_albums.adapter = adapter
    }

    fun initListAlbum(albums: ArrayList<AlbumRealm>) {
        adapter.reloadAdapter(albums)
    }

    override fun onBackPressed(): Boolean {
        return false
    }

    fun selectAlbum(albumIdSelected: String) {
        adapter.selectAlbum(albumIdSelected)
    }
}
