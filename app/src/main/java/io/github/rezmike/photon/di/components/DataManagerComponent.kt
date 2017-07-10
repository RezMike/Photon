package io.github.rezmike.photon.di.components

import dagger.Component
import io.github.rezmike.photon.data.managers.DataManager
import io.github.rezmike.photon.di.modules.LocalModule
import io.github.rezmike.photon.di.modules.NetworkModule
import javax.inject.Singleton

@Component(dependencies = arrayOf(AppComponent::class), modules = arrayOf(NetworkModule::class, LocalModule::class))
@Singleton
interface DataManagerComponent {
    fun inject(dataManager: DataManager)
}
