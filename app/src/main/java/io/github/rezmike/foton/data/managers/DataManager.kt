package io.github.rezmike.foton.data.managers

import io.github.rezmike.foton.App
import io.github.rezmike.foton.data.network.RestCallTransformer
import io.github.rezmike.foton.data.network.RestService
import io.github.rezmike.foton.di.components.DaggerDataManagerComponent
import io.github.rezmike.foton.di.components.DataManagerComponent
import io.github.rezmike.foton.di.modules.LocalModule
import io.github.rezmike.foton.di.modules.NetworkModule
import io.github.rezmike.foton.utils.DaggerService
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
        var component = DaggerService.getComponent(DataManagerComponent::class)
        if (component == null) {
            component = DaggerDataManagerComponent.builder()
                    .appComponent(App.appComponent)
                    .localModule(LocalModule())
                    .networkModule(NetworkModule())
                    .build();
            DaggerService.registerComponent(DataManagerComponent::class, component);
        }
        component!!.inject(this)
        restCallTransformer = RestCallTransformer()
    }

}