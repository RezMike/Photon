package io.github.rezmike.photon.di.components

import android.content.Context

import dagger.Component
import io.github.rezmike.photon.di.modules.AppModule

@Component(modules = arrayOf(AppModule::class))
interface AppComponent {
    fun context(): Context
}
