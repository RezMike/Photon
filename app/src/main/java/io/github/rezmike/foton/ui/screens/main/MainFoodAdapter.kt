package io.github.rezmike.foton.ui.screens.main

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import io.github.rezmike.foton.R
import io.github.rezmike.foton.data.storage.PhotoCardRealm

class MainFoodAdapter(val picasso: Picasso) : RecyclerView.Adapter<MainFoodAdapter.FoodViewHolder>() {

    private var items: ArrayList<PhotoCardRealm> = ArrayList()

    fun addItem(item: PhotoCardRealm) {
        items.add(item)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_wall_food, parent, false)
        return FoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodViewHolder?, position: Int) {
        holder?.bind(items[position])
    }

    override fun onViewDetachedFromWindow(holder: FoodViewHolder?) {
        super.onViewDetachedFromWindow(holder)
    }

    override fun getItemCount() = items.size


    inner class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val favorite: TextView
        val watcher: TextView
        val image: ImageView

        init {
            favorite = itemView.findViewById(R.id.favorite) as TextView
            watcher = itemView.findViewById(R.id.watcher) as TextView
            image = itemView.findViewById(R.id.image_food) as ImageView
        }


        fun bind(item: PhotoCardRealm) {
            favorite.text = item.favorits.toString()
            watcher.text = item.views.toString()
            picasso.load(item.photo)
                    .resize(300,200)
                    .into(image)
        }



    }
}