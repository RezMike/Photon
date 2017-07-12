package io.github.rezmike.photon.ui.screens.profile

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import io.github.rezmike.photon.R
import io.github.rezmike.photon.data.storage.realm.AlbumRealm
import kotlinx.android.synthetic.main.item_album.view.*
import kotlinx.android.synthetic.main.item_wall_food.view.*
import kotlinx.android.synthetic.main.ratio_image_1_1.view.*

class ProfileAlbumsAdapter(val picasso: Picasso, val itemClick: (AlbumRealm) -> Unit) : RecyclerView.Adapter<ProfileAlbumsAdapter.AlbumViewHolder>() {

    private var items: ArrayList<AlbumRealm> = ArrayList()

    fun addItem(item: AlbumRealm) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): AlbumViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_album, parent, false)
        return AlbumViewHolder(view, picasso, itemClick)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    class AlbumViewHolder(val view: View, val picasso: Picasso, val itemClick: (AlbumRealm) -> Unit) : RecyclerView.ViewHolder(view) {

        fun bind(item: AlbumRealm) {
            view.album_name.text = item.title
            view.count_photo_tx.text = item.photoCards.size.toString()
            view.favorites_tv.text = item.favorits.toString()
            view.watchers_tv.text = item.views.toString()
            if (item.photoCards.size > 0)
                picasso.load(item.photoCards[1].photo)
                        .resize(300, 200)
                        .into(view.food_img)
            view.setOnClickListener { itemClick(item) }
        }
    }

}
