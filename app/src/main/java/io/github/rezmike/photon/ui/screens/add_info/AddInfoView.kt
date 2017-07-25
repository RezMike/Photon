package io.github.rezmike.photon.ui.screens.add_info

import android.content.Context
import android.support.v4.view.ViewCompat
import android.support.v7.widget.GridLayoutManager
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import com.squareup.picasso.Picasso
import io.github.rezmike.photon.R
import io.github.rezmike.photon.data.storage.realm.AlbumRealm
import io.github.rezmike.photon.ui.screens.AbstractView
import io.github.rezmike.photon.utils.DaggerService
import kotlinx.android.synthetic.main.item_tag_flex_box.view.*
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
        add_tags_btn.setOnClickListener { presenter.onClickAddTag(tag_name_et.text.toString()) }
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

    fun initFlexBox(tags: ArrayList<String>) {
        flex_box.removeAllViews()
        if (!tags.isEmpty()) {
            tags.forEach {
                addTagOnFlexBox(it)
            }
        } else {
            wrap_flex_box.visibility = View.GONE
        }
    }

    fun addTagOnFlexBox(tag: String) {
        if (wrap_flex_box.visibility != View.VISIBLE) wrap_flex_box.visibility = View.VISIBLE
        flex_box.addView(createTagView(tag))
    }

    private fun createTagView(tag: String): View {
        val tagView = LayoutInflater.from(context).inflate(R.layout.item_tag_flex_box, this, false)
        tagView.tag_tv.text = "#${tag.toLowerCase()}"
        tagView.delete_btn.setOnClickListener { presenter.onClickDeleteTag(tag) }
        return tagView
    }

    fun clearTagEditText() {
        tag_name_et.text.clear()
    }

    override fun onBackPressed(): Boolean {
        return false
    }
}
