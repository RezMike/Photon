package io.github.rezmike.photon.ui.screens.user

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import io.github.rezmike.photon.R
import io.github.rezmike.photon.data.storage.realm.AlbumRealm
import kotlinx.android.synthetic.main.item_album_user.view.*
import kotlinx.android.synthetic.main.ratio_image_1_1.view.*

class UserAlbumAdapter(val picasso: Picasso, val itemClick: (AlbumRealm) -> Unit) : RecyclerView.Adapter<UserAlbumAdapter.UserAlbumViewHolder>() {
    private var items: ArrayList<AlbumRealm> = ArrayList()

    fun reloadAdapter(albums: ArrayList<AlbumRealm>) {
        items = albums
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): UserAlbumViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_album_user, parent, false)
        return UserAlbumViewHolder(view, picasso, itemClick)
    }

    override fun onBindViewHolder(holder: UserAlbumViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    class UserAlbumViewHolder(val view: View, val picasso: Picasso, val itemClick: (AlbumRealm) -> Unit) : RecyclerView.ViewHolder(view) {

        fun bind(item: AlbumRealm) {
            view.album_name.text = item.title
            view.count_photo_tx.text = item.photoCards.size.toString()
            if (item.photoCards.size > 0)
                picasso.load(item.photoCards[1].photo)
                        .resize(300, 200)
                        .into(view.food_img)
            view.setOnClickListener { itemClick(item) }
        }
    }
}