package io.github.rezmike.foton

import android.app.Application
import android.content.Context
import android.support.v7.app.AppCompatDelegate
import io.github.rezmike.foton.di.components.AppComponent
import io.github.rezmike.foton.di.components.DaggerAppComponent
import io.github.rezmike.foton.di.modules.AppModule
import io.github.rezmike.foton.utils.DaggerService
import io.realm.Realm
import mortar.MortarScope

class App : Application() {

    private var rootScope: MortarScope? = null

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }

    override fun getSystemService(name: String): Any {
        if (rootScope == null) createScope()
        return if (rootScope!!.hasService(name)) {
            rootScope!!.getService(name)
        } else {
            super.getSystemService(name)
        }
    }

    private fun createScope() {
        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(applicationContext))
                .build()
        rootScope = MortarScope.buildRootScope()
                .withService(DaggerService.SERVICE_NAME, appComponent)
                .build("Root")
        context = appComponent.context()
    }

    companion object {
        lateinit var appComponent: AppComponent
        lateinit var context: Context
    }
}