package io.github.rezmike.photon.di.modules

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import io.github.rezmike.photon.data.network.DateStringAdapter
import io.github.rezmike.photon.data.network.RestService
import io.github.rezmike.photon.utils.BASE_URL
import io.github.rezmike.photon.utils.MAX_CONNECTION_TIMEOUT
import io.github.rezmike.photon.utils.MAX_READ_TIMEOUT
import io.github.rezmike.photon.utils.MAX_WRITE_TIMEOUT
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return createClient()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return createRetrofit(okHttpClient)
    }

    @Provides
    @Singleton
    fun provideRestService(retrofit: Retrofit): RestService {
        return retrofit.create(RestService::class.java)
    }

    private fun createClient(): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .addNetworkInterceptor(StethoInterceptor())
                .connectTimeout(MAX_CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(MAX_READ_TIMEOUT, TimeUnit.MILLISECONDS)
                .writeTimeout(MAX_WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
                .build()
    }

    private fun createRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(createConvertFactory())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build()
    }

    private fun createConvertFactory(): Converter.Factory {
        return MoshiConverterFactory.create(Moshi.Builder().add(DateStringAdapter()).build())
    }
}
