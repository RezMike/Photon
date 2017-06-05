package io.github.rezmike.foton

import android.app.Application
import android.content.Context
import io.github.rezmike.foton.di.components.AppComponent
import io.github.rezmike.foton.di.components.DaggerAppComponent
import io.github.rezmike.foton.di.modules.AppModule
import io.github.rezmike.foton.di.modules.PicassoCacheModule
import io.github.rezmike.foton.ui.root.DaggerRootActivity_RootComponent
import io.github.rezmike.foton.ui.root.RootActivity
import io.github.rezmike.foton.ui.root.RootModule
import io.github.rezmike.foton.utils.DaggerService
import io.github.rezmike.foton.utils.ScreenScoper
import io.realm.Realm
import mortar.MortarScope
import mortar.bundler.BundleServiceRunner


class App : Application() {

    private var mRootScope: MortarScope? = null
    private lateinit var mRootActivityScope: MortarScope
    private lateinit var mRootActivityRootComponent: RootActivity.RootComponent

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
    }

    override fun getSystemService(name: String): Any {
        if (mRootScope == null) createScopes()
        return if (mRootScope?.hasService(name) ?: false) {
            mRootScope?.getService(name)
        } else {
            super.getSystemService(name)
        }
    }

    private fun createScopes() {
        createAppComponent()
        createRootActivityComponent()

        mRootScope = MortarScope.buildRootScope()
                .withService(DaggerService.SERVICE_NAME, appComponent)
                .build("Root")

        mRootActivityScope = mRootScope!!.buildChild()
                .withService(DaggerService.SERVICE_NAME, mRootActivityRootComponent)
                .withService(BundleServiceRunner.SERVICE_NAME, BundleServiceRunner())
                .build(RootActivity::class.java.name)

        ScreenScoper.registerScope(mRootScope!!)
        ScreenScoper.registerScope(mRootActivityScope)
    }

    private fun createAppComponent() {
        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(applicationContext))
                .build()
        context = appComponent.context()
    }

    private fun createRootActivityComponent() {
        mRootActivityRootComponent = DaggerRootActivity_RootComponent.builder()
                .appComponent(appComponent)
                .rootModule(RootModule())
                .picassoCacheModule(PicassoCacheModule())
                .build()
    }

    companion object {
        lateinit var appComponent: AppComponent
        lateinit var context: Context
    }
}