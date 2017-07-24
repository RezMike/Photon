package io.github.rezmike.photon.ui.screens.add_info

import android.content.Context
import android.support.v4.view.ViewCompat
import android.support.v7.widget.GridLayoutManager
import android.util.AttributeSet
import com.squareup.picasso.Picasso
import io.github.rezmike.photon.data.storage.realm.AlbumRealm
import io.github.rezmike.photon.ui.screens.AbstractView
import io.github.rezmike.photon.utils.DaggerService
import kotlinx.android.synthetic.main.ratio_image_16_9.view.*
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

    fun setPhoto(photoUri: String) {
        picasso.load(photoUri)
                .resize(300, 200)
                .into(food_img)
    }

    fun initListAlbum(albums: ArrayList<AlbumRealm>, albumIdSelected: String?) {
        adapter.reloadAdapter(albums)
        selectAlbum(albumIdSelected)
    }

    fun selectAlbum(albumIdSelected: String?) {
        adapter.selectAlbum(albumIdSelected)
    }

    override fun onBackPressed(): Boolean {
        return false
    }
}
