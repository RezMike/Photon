package io.github.rezmike.photon.ui.activities.root

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.TabLayout
import android.support.graphics.drawable.VectorDrawableCompat
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.AlertDialog
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.squareup.picasso.Picasso
import flow.Direction
import flow.Flow
import io.github.rezmike.photon.App
import io.github.rezmike.photon.BuildConfig
import io.github.rezmike.photon.R
import io.github.rezmike.photon.di.components.AppComponent
import io.github.rezmike.photon.di.modules.PicassoCacheModule
import io.github.rezmike.photon.di.scopes.RootScope
import io.github.rezmike.photon.ui.activities.BaseActivity
import io.github.rezmike.photon.ui.others.MenuItemHolder
import io.github.rezmike.photon.ui.screens.IView
import io.github.rezmike.photon.ui.screens.auth.AuthScreen
import io.github.rezmike.photon.ui.screens.main.MainScreen
import io.github.rezmike.photon.ui.screens.profile.ProfileScreen
import io.github.rezmike.photon.ui.screens.upload.UploadScreen
import io.github.rezmike.photon.utils.DaggerService
import kotlinx.android.synthetic.main.activity_root.*
import mortar.MortarScope
import mortar.bundler.BundleServiceRunner
import javax.inject.Inject

class RootActivity : BaseActivity(), IActionBarView {

    var actionBarMenuItems: ArrayList<MenuItemHolder> = ArrayList()

    @Inject
    lateinit var presenter: RootPresenter

    override fun attachBaseContext(newBase: Context) {
        val newContext = Flow.configure(newBase, this)
                .defaultKey(MainScreen())
                .dispatcher(TreeKeyDispatcher(this))
                .install()
        super.attachBaseContext(newContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_root)
        BundleServiceRunner.getBundleServiceRunner(this).onCreate(savedInstanceState)
        DaggerService.getDaggerComponent<RootComponent>(this).inject(this)
        presenter.takeView(this)
        initBottomNavigation()
        setSupportActionBar(toolbar)
    }

    override fun onDestroy() {
        presenter.dropView(this)
        super.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        BundleServiceRunner.getBundleServiceRunner(this).onSaveInstanceState(outState)
    }

    override fun getSystemService(name: String): Any {
        val activityScope = MortarScope.findChild(applicationContext, javaClass.name) ?:
                MortarScope.buildChild(applicationContext)
                        .withService(DaggerService.SERVICE_NAME, createComponent())
                        .withService(BundleServiceRunner.SERVICE_NAME, BundleServiceRunner())
                        .build(javaClass.name)

        return if (activityScope.hasService(name)) {
            activityScope.getService(name)
        } else {
            super.getSystemService(name)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        presenter.onActivityResult(requestCode, resultCode, data)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        presenter.onRequestPermissionResult(requestCode, permissions, grantResults)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menu?.clear()
        if (actionBarMenuItems.isNotEmpty() && menu != null) {
            actionBarMenuItems.forEach {
                menu.add(it.itemTitle)
                        .setIcon(it.iconRes)
                        .setOnMenuItemClickListener(it.listener)
                        .setShowAsAction(it.showAsActionFlag)
            }
            return true
        }
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            onBackPressed()
            return true;
        } else {
            return super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        if (getCurrentScreen() == null || !getCurrentScreen()!!.onBackPressed() && !Flow.get(this).goBack()) {
            val alertDialog = AlertDialog.Builder(this)
                    .setTitle(R.string.exit_dialog_title)
                    .setPositiveButton(R.string.dialog_yes) { _, _ -> finish() }
                    .setNegativeButton(R.string.dialog_no, null)
                    .create()
            alertDialog.show()
        }
    }

    private fun getCurrentScreen(): IView? {
        return root_frame.getChildAt(0) as IView
    }

    //region ======================== Initiation ========================

    private fun createComponent(): Any? = DaggerRootActivity_RootComponent.builder()
            .appComponent(App.appComponent)
            .rootModule(RootModule())
            .picassoCacheModule(PicassoCacheModule())
            .build()

    private fun initBottomNavigation() {
        navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_main -> {
                    presenter.onClickMain()
                    true
                }
                R.id.navigation_profile -> {
                    presenter.onClickProfile()
                    true
                }
                R.id.navigation_upload -> {
                    presenter.onClickUpload()
                    true
                }
                else -> false
            }
        }
    }

    //endregion

    //region ======================== IRootView ========================

    fun showMessage(message: String) {
        Snackbar.make(coordinator_container, message, Snackbar.LENGTH_LONG).show()
    }

    fun showMessage(stringResId: Int) {
        showMessage(getString(stringResId))
    }

    fun showToastMessage(stringResId: Int) {
        showToastMessage(getString(stringResId))
    }

    fun showToastMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun showError(e: Throwable) {
        if (BuildConfig.DEBUG) {
            showMessage(e.message ?: "Some error")
            e.printStackTrace()
        } else {
            showMessage(R.string.sorry_something_wrong)
            //FirebaseCrash.log("ROOT VIEW EXCEPTION")
            //FirebaseCrash.report(e)
        }
    }

    fun showLoad() {
        showProgress()
    }

    fun hideLoad() {
        hideProgress()
    }

    fun showMainScreen() {
        Flow.get(this).replaceHistory(MainScreen(), Direction.REPLACE)
    }

    fun showProfileScreen() {
        Flow.get(this).replaceHistory(ProfileScreen(), Direction.REPLACE)
    }

    fun showAuthScreen() {
        Flow.get(this).replaceHistory(AuthScreen(), Direction.REPLACE)
    }

    fun showUploadScreen() {
        Flow.get(this).replaceHistory(UploadScreen(), Direction.REPLACE)
    }

    fun setCurrentBottomItem(item: BottomBarItems) {
        val itemId = when (item) {
            BottomBarItems.MAIN -> R.id.navigation_main
            BottomBarItems.PROFILE -> R.id.navigation_profile
            BottomBarItems.UPLOAD -> R.id.navigation_upload
        }
        navigation.menu.findItem(itemId).isChecked = true
    }

    fun isAllGranted(permissions: Array<String>, allGranted: Boolean): Boolean {
        var granted = allGranted
        for (permission in permissions) {
            val selfPermission = ContextCompat.checkSelfPermission(this, permission)
            if (selfPermission != PackageManager.PERMISSION_GRANTED) {
                granted = false
                break
            }
        }
        return granted
    }

    fun sendSharingIntent(link: String) {
        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.putExtra(Intent.EXTRA_TEXT, link)
        shareIntent.type = "text/plain"
        if (shareIntent.resolveActivity(packageManager) != null) startActivity(shareIntent)
    }

    //endregion

    //region ======================== IActionBarView ========================

    override fun setTitleBar(title: CharSequence) {
        supportActionBar?.title = title
    }

    override fun setVisibleBar(enable: Boolean) {
        if (supportActionBar != null) {
            if (enable) toolbar.visibility = View.VISIBLE
            else toolbar.visibility = View.GONE
        }
    }

    override fun setBackArrow(enable: Boolean) {
        supportActionBar?.setDisplayHomeAsUpEnabled(enable)
    }

    override fun setOverFlowIcon(iconRes: Int?) {
        if (iconRes != null) toolbar.overflowIcon = VectorDrawableCompat.create(resources, iconRes, null)
    }

    override fun setMenuItems(items: ArrayList<MenuItemHolder>) {
        actionBarMenuItems = items
        supportInvalidateOptionsMenu()
    }

    fun setTabLayout(pager: ViewPager) {
        val tabView = TabLayout(this)
        tabView.setupWithViewPager(pager)
        appbar_layout.addView(tabView)
        pager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabView))
    }

    fun removeTabLayout() {
        val tabView = appbar_layout.getChildAt(1)
        if (tabView != null && tabView is TabLayout) {
            appbar_layout.removeView(tabView)
        }
    }

    //endregion

    //region ======================== DI ========================

    @dagger.Component(dependencies = arrayOf(AppComponent::class), modules = arrayOf(RootModule::class, PicassoCacheModule::class))
    @RootScope
    interface RootComponent {
        fun inject(activity: RootActivity)

        fun inject(presenter: RootPresenter)

        fun getRootPresenter(): RootPresenter

        fun getAccountModel(): AccountModel

        fun getPicasso(): Picasso
    }

    //endregion
}
