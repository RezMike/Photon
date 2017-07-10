package io.github.rezmike.photon.di.components

import com.squareup.picasso.Picasso

import dagger.Component
import io.github.rezmike.photon.di.modules.PicassoCacheModule
import io.github.rezmike.photon.di.scopes.RootScope

@Component(dependencies = arrayOf(AppComponent::class), modules = arrayOf(PicassoCacheModule::class))
@RootScope
interface PicassoComponent {
    fun getPicasso(): Picasso
}
