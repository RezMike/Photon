package io.github.rezmike.foton.di.modules

import android.content.Context

import com.jakewharton.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso

import dagger.Module
import dagger.Provides
import io.github.rezmike.foton.di.scopes.RootScope

@Module
class PicassoCacheModule {

    @Provides
    @RootScope
    fun providePicasso(context: Context): Picasso {
        val okHttp3Downloader = OkHttp3Downloader(context)
        val picasso = Picasso.Builder(context)
                .downloader(okHttp3Downloader)
                .indicatorsEnabled(true)
                .build()
        Picasso.setSingletonInstance(picasso)
        return picasso
    }
}
