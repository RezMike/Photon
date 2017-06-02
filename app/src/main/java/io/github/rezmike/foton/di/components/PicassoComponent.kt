package io.github.rezmike.foton.di.components

import com.squareup.picasso.Picasso

import dagger.Component
import io.github.rezmike.foton.di.modules.PicassoCacheModule
import io.github.rezmike.foton.di.scopes.RootScope

@Component(dependencies = arrayOf(AppComponent::class), modules = arrayOf(PicassoCacheModule::class))
@RootScope
interface PicassoComponent {
    fun getPicasso(): Picasso
}
