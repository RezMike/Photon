package io.github.rezmike.photon.di.components

import dagger.Component
import io.github.rezmike.photon.di.modules.ModelModule
import io.github.rezmike.photon.ui.abstracts.AbstractModel
import javax.inject.Singleton

@Component(modules = arrayOf(ModelModule::class))
@Singleton
interface ModelComponent {
    fun inject(abstractModel: AbstractModel)
}
