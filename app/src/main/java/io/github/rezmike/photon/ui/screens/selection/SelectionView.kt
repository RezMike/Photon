package io.github.rezmike.photon.ui.screens.selection

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import io.github.rezmike.photon.R
import io.github.rezmike.photon.ui.screens.AbstractView
import io.github.rezmike.photon.utils.DaggerService
import kotlinx.android.synthetic.main.screen_selection.view.*

class SelectionView(context: Context, attrs: AttributeSet?) : AbstractView<SelectionPresenter, SelectionView>(context, attrs) {

    override fun initDagger(context: Context) {
        DaggerService.getDaggerComponent<SelectionScreen.Component>(context).inject(this)
    }

    fun getPager() = findViewById(R.id.selection_pager) as ViewPager

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        selection_pager.adapter = SelectionAdapter(context)
    }

    override fun onBackPressed(): Boolean {
        return false
    }
}