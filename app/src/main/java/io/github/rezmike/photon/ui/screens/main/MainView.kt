package io.github.rezmike.photon.ui.screens.main

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.util.AttributeSet
import com.squareup.picasso.Picasso
import io.github.rezmike.photon.data.storage.realm.PhotoCardRealm
import io.github.rezmike.photon.ui.screens.AbstractView
import io.github.rezmike.photon.utils.DaggerService
import kotlinx.android.synthetic.main.screen_main.view.*
import javax.inject.Inject

class MainView(context: Context, attrs: AttributeSet?) : AbstractView<MainPresenter, MainView>(context, attrs) {

    @Inject
    lateinit var picasso: Picasso

    val adapter = MainFoodAdapter(picasso, { presenter.onClickItem(it) })

    override fun initDagger(context: Context) {
        DaggerService.getDaggerComponent<MainScreen.Component>(context).inject(this)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        list_food.layoutManager = GridLayoutManager(context, 2)
        list_food.adapter = adapter
    }

    fun addItem(item: PhotoCardRealm) {
        adapter.addItem(item)
    }

    override fun onBackPressed(): Boolean {
        return false
    }
}
