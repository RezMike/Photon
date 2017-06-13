package io.github.rezmike.foton.ui.root

import io.github.rezmike.foton.utils.DaggerService
import mortar.MortarScope
import mortar.Presenter
import mortar.bundler.BundleService
import android.support.v4.view.ViewPager
import android.view.MenuItem

class RootPresenter : Presenter<RootActivity>() {

    companion object {
        val DEFAULT_MODE = 0
        val TAB_MODE = 1
    }

    var currentItem: Int = RootActivity.MAIN_SCREEN

    override fun onEnterScope(scope: MortarScope) {
        super.onEnterScope(scope)
        DaggerService.getDaggerComponent<RootActivity.RootComponent>(scope).inject(this)
    }

    override fun extractBundleService(view: RootActivity): BundleService {
        return BundleService.getBundleService(view)
    }

    fun onClickMain() {
        saveItemSelected(RootActivity.MAIN_SCREEN)
    }

    fun onClickProfile() {
        saveItemSelected(RootActivity.PROFILE_SCREEN)
    }

    fun onClickLoad() {
        saveItemSelected(RootActivity.LOAD_SCREEN)
    }

    private fun saveItemSelected(item: Int) {
        if (currentItem != item) {
            currentItem = item
            view?.turnScreen(item)
        }
    }

    fun newActionBarBuilder() = this.ActionBarBuilder()

    inner class ActionBarBuilder {
        private var isGoBack = false
        private var isVisible = true
        private var mTitle: CharSequence = ""
        private var mItems: ArrayList<MenuItemHolder> = ArrayList()
        private var mPager: ViewPager? = null
        private var mToolbarMode = DEFAULT_MODE

        fun setBackArrow(enable: Boolean): ActionBarBuilder {
            this.isGoBack = enable
            return this
        }

        fun setVisible(enable: Boolean): ActionBarBuilder {
            this.isVisible = enable
            return this
        }

        fun setTitle(title: CharSequence): ActionBarBuilder {
            this.mTitle = title
            return this
        }

        fun addAction(menuItem: MenuItemHolder): ActionBarBuilder {
            this.mItems.add(menuItem)
            return this
        }

        fun setTab(pager: ViewPager): ActionBarBuilder {
            this.mToolbarMode = TAB_MODE
            this.mPager = pager
            return this
        }

        fun build() {
            if (view != null) {
                val activity = view
                activity.setTitleBar(mTitle)
                activity.setVisibleBar(isVisible)
                activity.setBackArrow(isGoBack)
                activity.setMenuItem(mItems)
                if (mToolbarMode == TAB_MODE) {
                    activity.setTabLayout(mPager!!)
                } else {
                    activity.removeTabLayout()
                }
            }
        }
    }


}