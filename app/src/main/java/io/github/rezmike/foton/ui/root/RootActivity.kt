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
import io.github.rezmike.foton.flow.TreeKeyDispatcher
import io.github.rezmike.foton.mortar.ScreenScoper
import io.github.rezmike.foton.ui.abstracts.BaseActivity
import io.github.rezmike.foton.ui.screens.splash.SplashScreen
import mortar.MortarScope
import mortar.bundler.BundleServiceRunner

class RootActivity : BaseActivity() {

    override fun attachBaseContext(newBase: Context?) {
        val newContext = Flow.configure(newBase!!, this)
                .defaultKey(SplashScreen())
                .dispatcher(TreeKeyDispatcher(this))
                .install()
        super.attachBaseContext(newContext)
    }

    override fun getSystemService(name: String?): Any {

        val mActivityScope = MortarScope.findChild(applicationContext, javaClass.name)
                ?:
                MortarScope.buildChild(applicationContext)
                        .withService(ScreenScoper.SERVICE_NAME, createComponent())
                        .withService(BundleServiceRunner.SERVICE_NAME, BundleServiceRunner())
                        .build(javaClass.name)

        return if (mActivityScope?.hasService(name) ?: false) {
            mActivityScope?.getService(name)
        } else {
            super.getSystemService(name)
        }
    }

    private fun createComponent(): Any? = DaggerRootActivity_RootComponent.builder()
            .appComponent(App.appComponent)
            .rootModule(RootModule())
            .picassoCacheModule(PicassoCacheModule())
            .build()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BundleServiceRunner.getBundleServiceRunner(this).onCreate(savedInstanceState)
        setContentView(R.layout.activity_root)
        ScreenScoper.getDaggerComponent<RootComponent>(this).inject(this)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        BundleServiceRunner.getBundleServiceRunner(this).onSaveInstanceState(outState)
    }

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