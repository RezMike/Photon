package io.github.rezmike.foton.ui.screens.main

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import io.github.rezmike.foton.R
import io.github.rezmike.foton.data.storage.PhotoCardRealm
import kotlinx.android.synthetic.main.item_wall_food.view.*
import kotlinx.android.synthetic.main.ratio_image_1_1.view.*

class MainFoodAdapter(val picasso: Picasso, val itemClick: (PhotoCardRealm) -> Unit) : RecyclerView.Adapter<MainFoodAdapter.FoodViewHolder>() {

    private var items: ArrayList<PhotoCardRealm> = ArrayList()

    fun addItem(item: PhotoCardRealm) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_wall_food, parent, false)
        return FoodViewHolder(view, picasso, itemClick)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    class FoodViewHolder(val view: View, val picasso: Picasso, val itemClick: (PhotoCardRealm) -> Unit) : RecyclerView.ViewHolder(view) {

        fun bind(item: PhotoCardRealm) {
            view.favorites_tv.text = item.favorits.toString()
            view.watchers_tv.text = item.views.toString()
            picasso.load(item.photo)
                    .resize(300, 200)
                    .into(view.food_img)
            view.setOnClickListener { itemClick(item) }
        }
    }
}