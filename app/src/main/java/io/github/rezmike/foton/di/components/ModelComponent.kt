package io.github.rezmike.foton.di.components

import dagger.Component
import io.github.rezmike.foton.di.modules.ModelModule
import io.github.rezmike.foton.ui.abstracts.AbstractModel
import javax.inject.Singleton

@Component(modules = arrayOf(ModelModule::class))
@Singleton
interface ModelComponent {
    fun inject(abstractModel: AbstractModel)
}
