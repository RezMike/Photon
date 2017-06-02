package io.github.rezmike.foton.di.components

import javax.inject.Singleton

import dagger.Component
import io.github.rezmike.foton.di.modules.ModelModule
import io.github.rezmike.foton.ui.abstracts.AbstractModel

@Component(modules = arrayOf(ModelModule::class))
@Singleton
interface ModelComponent {
    fun inject(abstractModel: AbstractModel)
}
