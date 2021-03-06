package io.github.rezmike.foton.data.managers

import io.github.rezmike.foton.App
import io.github.rezmike.foton.data.network.RestCallTransformer
import io.github.rezmike.foton.data.network.RestService
import io.github.rezmike.foton.di.components.DaggerDataManagerComponent
import io.github.rezmike.foton.di.modules.LocalModule
import io.github.rezmike.foton.di.modules.NetworkModule
import javax.inject.Inject

class DataManager private constructor() {

    private var restCallTransformer: RestCallTransformer<Any>

    @Inject
    lateinit var preferencesManager: PreferencesManager
    @Inject
    lateinit var realmManager: RealmManager
    @Inject
    lateinit var restService: RestService

    companion object {
        val INSTANCE = DataManager()
    }

    init {
        DaggerDataManagerComponent.builder()
                .appComponent(App.appComponent)
                .localModule(LocalModule())
                .networkModule(NetworkModule())
                .build().inject(this)

        restCallTransformer = RestCallTransformer()
    }

}