package io.github.rezmike.foton

import android.app.Application
import android.content.Context
import io.github.rezmike.foton.di.components.AppComponent
import io.github.rezmike.foton.di.components.DaggerAppComponent
import io.github.rezmike.foton.di.modules.AppModule
import io.github.rezmike.foton.ui.root.RootActivity
import io.github.rezmike.foton.mortar.ScreenScoper
import io.realm.Realm
import mortar.MortarScope


class App : Application() {

    private var mRootScope: MortarScope? = null

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

        mRootScope = MortarScope.buildRootScope()
                .withService(ScreenScoper.SERVICE_NAME, appComponent)
                .build("Root")

    }

    private fun createAppComponent() {
        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(applicationContext))
                .build()
        context = appComponent.context()
    }


    companion object {
        lateinit var appComponent: AppComponent
        lateinit var context: Context
    }
}