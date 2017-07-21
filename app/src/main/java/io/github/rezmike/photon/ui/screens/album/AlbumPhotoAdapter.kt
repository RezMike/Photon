package io.github.rezmike.photon.ui.screens.album

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import io.github.rezmike.photon.R
import io.github.rezmike.photon.data.storage.realm.PhotoCardRealm
import kotlinx.android.synthetic.main.ratio_image_1_1.view.*

class AlbumPhotoAdapter(val picasso: Picasso, val itemClick: (PhotoCardRealm) -> Unit) : RecyclerView.Adapter<AlbumPhotoAdapter.PhotoViewHolder>() {

    private var items: ArrayList<PhotoCardRealm> = ArrayList()

    fun reloadAdapter(photo: ArrayList<PhotoCardRealm>) {
        items.clear()
        items = photo
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: PhotoViewHolder?, position: Int) {
        holder?.bind(items[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): PhotoViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.ratio_image_1_1, parent, false)
        return PhotoViewHolder(view, picasso, itemClick)
    }

    override fun getItemCount() = items.size

    class PhotoViewHolder(val view: View, val picasso: Picasso, val itemClick: (PhotoCardRealm) -> Unit) : RecyclerView.ViewHolder(view) {

        fun bind(item: PhotoCardRealm) {
            picasso.load(item.photo)
                    .resize(100, 100)
                    .into(view.food_img)

            view.setOnClickListener { itemClick(item) }
        }
    }
}