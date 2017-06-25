package io.github.rezmike.foton.ui.screens.main

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.util.AttributeSet
import com.squareup.picasso.Picasso
import io.github.rezmike.foton.data.storage.PhotoCardRealm
import io.github.rezmike.foton.ui.abstracts.AbstractView
import io.github.rezmike.foton.utils.DaggerService
import kotlinx.android.synthetic.main.screen_main.view.*
import javax.inject.Inject

class MainView(context: Context, attrs: AttributeSet?) : AbstractView<MainPresenter, MainView>(context, attrs) {

    @Inject
    lateinit var picasso: Picasso

    val adapter = MainFoodAdapter(picasso, { presenter.onClickItem(it) })

    override fun initDagger(context: Context) {
        DaggerService.getDaggerComponent<MainScreen.Component>(context).inject(this)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        list_food.layoutManager = GridLayoutManager(context, 2)
        list_food.adapter = adapter

    }

    //region ======================== IMainView ========================

    fun addItem(item: PhotoCardRealm) {
        adapter.addItem(item)
    }

    //endregion
}
