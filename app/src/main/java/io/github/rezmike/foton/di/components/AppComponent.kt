package io.github.rezmike.foton.di.components

import android.content.Context

import dagger.Component
import io.github.rezmike.foton.di.modules.AppModule

@Component(modules = arrayOf(AppModule::class))
interface AppComponent {
    fun context(): Context
}
