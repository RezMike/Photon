package io.github.rezmike.foton.di.modules

import android.content.Context
import com.facebook.stetho.Stetho
import com.uphyca.stetho_realm.RealmInspectorModulesProvider
import dagger.Module
import dagger.Provides
import io.github.rezmike.foton.data.managers.PreferencesManager
import io.github.rezmike.foton.data.managers.RealmManager
import javax.inject.Singleton

@Module
class LocalModule {

    @Provides
    @Singleton
    fun providePreferencesManager(context: Context) = PreferencesManager(context)

    @Provides
    @Singleton
    fun provideRealmManager(context: Context): RealmManager {
        Stetho.initialize(Stetho.newInitializerBuilder(context)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(context))
                .enableWebKitInspector(RealmInspectorModulesProvider.builder(context).build())
                .build())
        return RealmManager()
    }
}
