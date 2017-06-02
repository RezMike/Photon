package io.github.rezmike.foton.di.modules

import android.content.Context

import dagger.Module
import dagger.Provides

@Module
class AppModule(private val context: Context) {

    @Provides
    fun provideContext(): Context {
        return context
    }
}
