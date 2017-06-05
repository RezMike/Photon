package io.github.rezmike.foton.ui.root

import android.content.Context
import android.os.Bundle
import com.squareup.picasso.Picasso
import flow.Flow
import io.github.rezmike.foton.App
import io.github.rezmike.foton.R
import io.github.rezmike.foton.di.components.AppComponent
import io.github.rezmike.foton.di.modules.PicassoCacheModule
import io.github.rezmike.foton.di.scopes.RootScope
import io.github.rezmike.foton.ui.abstracts.AbstractScreen
import io.github.rezmike.foton.ui.abstracts.BaseActivity
import io.github.rezmike.foton.ui.screens.navigation.NavigationScreen
import io.github.rezmike.foton.ui.screens.profile.ProfileScreen
import io.github.rezmike.foton.ui.screens.splash.SplashScreen
import io.github.rezmike.foton.utils.DaggerService
import io.github.rezmike.foton.utils.TreeKeyDispatcher
import mortar.MortarScope
import mortar.bundler.BundleServiceRunner
import kotlinx.android.synthetic.main.activity_root.*
import javax.inject.Inject

class RootActivity : BaseActivity() {

    companion object {
        val MAIN_SCREEN = 0
        val PROFILE_SCREEN = 1
        val LOAD_SCREEN = 2
    }

    @Inject
    lateinit var presenter: RootPresenter

    override fun attachBaseContext(newBase: Context) {
        val newContext = Flow.configure(newBase, this)
                .defaultKey(SplashScreen())
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
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.dropView(this)
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
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_profile -> {
                    presenter.onClickProfile()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_load -> {
                    presenter.onClickLoad()
                    return@setOnNavigationItemSelectedListener true
                }
                else -> return@setOnNavigationItemSelectedListener false
            }
        }
    }

    //endregion

    //region ======================== IRootView ========================
    fun turnScreen(item: Int) {
        var screen: Any? = null
        when (item) {
            MAIN_SCREEN -> {
                //TODO сюда нужно вставлять скрины
                screen = SplashScreen()
            }
            PROFILE_SCREEN -> {
                screen = ProfileScreen()//TODO сюда нужно вставлять скрины
            }
            LOAD_SCREEN -> {
                //TODO сюда нужно вставлять скрины
                screen = SplashScreen()
            }
        }
        if (screen != null) {
            Flow.get(this).set(screen)
        }
    }
    //endregion

    //region ======================== DI ========================

    @dagger.Component(dependencies = arrayOf(AppComponent::class), modules = arrayOf(RootModule::class, PicassoCacheModule::class))
    @RootScope
    interface RootComponent {
        fun inject(activity: RootActivity)

        fun inject(presenter: RootPresenter)

        //val rootPresenter: RootPresenter

        //fun getAccountModel() : AccountModel

        //fun getRootPresenter() : RootPresenter

        fun getPicasso(): Picasso
    }


    //endregion
}