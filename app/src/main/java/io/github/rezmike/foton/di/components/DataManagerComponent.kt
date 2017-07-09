package io.github.rezmike.foton.di.components

import dagger.Component
import io.github.rezmike.foton.data.managers.DataManager
import io.github.rezmike.foton.di.modules.LocalModule
import io.github.rezmike.foton.di.modules.NetworkModule
import javax.inject.Singleton

@Component(dependencies = arrayOf(AppComponent::class), modules = arrayOf(NetworkModule::class, LocalModule::class))
@Singleton
interface DataManagerComponent {
    fun inject(dataManager: DataManager)
}
