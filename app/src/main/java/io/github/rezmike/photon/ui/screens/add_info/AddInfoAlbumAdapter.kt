package io.github.rezmike.photon.ui.screens.add_info

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import io.github.rezmike.photon.R
import io.github.rezmike.photon.data.storage.realm.AlbumRealm
import kotlinx.android.synthetic.main.item_album_add_info.view.*
import kotlinx.android.synthetic.main.ratio_image_1_1.view.*

class AddInfoAlbumAdapter(val picasso: Picasso, val itemClick: (String) -> Unit) : RecyclerView.Adapter<AddInfoAlbumAdapter.AddInfoAlbumViewHolder>() {

    private var items: ArrayList<AlbumRealm> = ArrayList()
    private var albumIdSelected = ""

    fun reloadAdapter(albums: ArrayList<AlbumRealm>) {
        items = albums
        notifyDataSetChanged()
    }

    fun selectAlbum(albumIdSelected: String) {
        this.albumIdSelected = albumIdSelected
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): AddInfoAlbumViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_album_add_info, parent, false)
        return AddInfoAlbumViewHolder(view, picasso, itemClick)
    }

    override fun onBindViewHolder(holder: AddInfoAlbumViewHolder, position: Int) {
        holder.bind(items[position], items[position].id == albumIdSelected)
    }

    override fun getItemCount() = items.size

    class AddInfoAlbumViewHolder(val view: View, val picasso: Picasso, val itemClick: (String) -> Unit) : RecyclerView.ViewHolder(view) {

        fun bind(item: AlbumRealm, isSelected: Boolean) {
            view.album_name.text = item.title
            view.count_photo_tx.text = item.photoCards.size.toString()
            if (item.photoCards.size > 0)
                picasso.load(item.photoCards[0].photo)
                        .resize(300, 200)
                        .into(view.food_img)
            view.setOnClickListener { itemClick(item.id) }
            changeState(isSelected)
        }

        private fun changeState(isSelected: Boolean) {
            if (isSelected) view.selected_img.visibility = View.VISIBLE
            else view.selected_img.visibility = View.GONE
        }
    }

}