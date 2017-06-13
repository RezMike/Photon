package io.github.rezmike.foton.ui.root

import android.content.Context
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.view.Menu
import android.view.View
import com.squareup.picasso.Picasso
import flow.Flow
import io.github.rezmike.foton.App
import io.github.rezmike.foton.R
import io.github.rezmike.foton.di.components.AppComponent
import io.github.rezmike.foton.di.modules.PicassoCacheModule
import io.github.rezmike.foton.di.scopes.RootScope
import io.github.rezmike.foton.ui.abstracts.AbstractScreen
import io.github.rezmike.foton.ui.abstracts.BaseActivity
import io.github.rezmike.foton.ui.screens.main.MainScreen
import io.github.rezmike.foton.ui.screens.profile.ProfileScreen
import io.github.rezmike.foton.ui.screens.splash.SplashScreen
import io.github.rezmike.foton.utils.DaggerService
import io.github.rezmike.foton.utils.TreeKeyDispatcher
import kotlinx.android.synthetic.main.activity_root.*
import mortar.MortarScope
import mortar.bundler.BundleServiceRunner
import javax.inject.Inject

class RootActivity : BaseActivity(), IActionBar {

    var mActionBarMenuItems: ArrayList<MenuItemHolder> = ArrayList()

    companion object {
        val MAIN_SCREEN = 0
        val PROFILE_SCREEN = 1
        val LOAD_SCREEN = 2
    }

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
        BundleServiceRunner.getBundleServiceRunner(this).onCreate(savedInstanceState)
        setContentView(R.layout.activity_root)
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

    private fun createComponent(): Any? = DaggerRootActivity_RootComponent.builder()
            .appComponent(App.appComponent)
            .rootModule(RootModule())
            .picassoCacheModule(PicassoCacheModule())
            .build()

    //region ======================== Initiation ========================

    private fun initBottomNavigation() {
        navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_main -> {
                    presenter.onClickMain()
                    true
                }
                R.id.navigation_profile -> {
                    presenter.onClickProfile()
                    true
                }
                R.id.navigation_load -> {
                    presenter.onClickLoad()
                    true
                }
                else -> false
            }
        }
    }

    //endregion

    //region ======================== IRootView ========================

    fun turnScreen(item: Int) {
        val screen: AbstractScreen<*>
        when (item) {
            MAIN_SCREEN -> screen = MainScreen()
            PROFILE_SCREEN -> screen = ProfileScreen()
            LOAD_SCREEN -> screen = SplashScreen()
            else -> return
        }
        Flow.get(this).set(screen)
    }

    //endregion

    //region ======================== IActionBar ========================

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

    override fun setMenuItem(items: ArrayList<MenuItemHolder>) {
        mActionBarMenuItems = items
        supportInvalidateOptionsMenu()
    }

    override fun setTabLayout(pager: ViewPager) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun removeTabLayout() {

    }

    //endregion

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        if (mActionBarMenuItems.isNotEmpty()) {
            mActionBarMenuItems.forEach {
                menu?.add(it.itemTitle)
                        ?.setIcon(it.iconRes)
                        ?.setOnMenuItemClickListener(it.listener)
                        ?.setShowAsAction(it.showAsActionFlag)
            }
        }
        return super.onPrepareOptionsMenu(menu)
    }

    //region ======================== DI ========================

    @dagger.Component(dependencies = arrayOf(AppComponent::class), modules = arrayOf(RootModule::class, PicassoCacheModule::class))
    @RootScope
    interface RootComponent {
        fun inject(activity: RootActivity)

        fun inject(presenter: RootPresenter)

//        val rootPresenter: RootPresenter

        //fun getAccountModel() : AccountModel

        fun getRootPresenter() : RootPresenter

        fun getPicasso(): Picasso
    }

    //endregion
}
