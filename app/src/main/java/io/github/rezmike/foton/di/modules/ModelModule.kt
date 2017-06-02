package io.github.rezmike.foton.di.modules

import com.birbit.android.jobqueue.JobManager
import com.birbit.android.jobqueue.config.Configuration

import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import io.github.rezmike.foton.App
import io.github.rezmike.foton.data.managers.DataManager
import io.github.rezmike.foton.utils.AppConfig

@Module
class ModelModule {

    @Provides
    @Singleton
    fun provideDataManager(): DataManager = DataManager.instance

    @Provides
    @Singleton
    fun provideJobManager(): JobManager {

        val configuration = Configuration.Builder(App.context)
                .minConsumerCount(AppConfig.JOB_MIN_CONSUMER_COUNT) // always keep at least one consumer alive
                .maxConsumerCount(AppConfig.JOB_MAX_CONSUMER_COUNT) // up to 3 consumer at a time
                .loadFactor(AppConfig.JOB_LOAD_FACTOR) // 3 jobs per consumer
                .consumerKeepAlive(AppConfig.JOB_KEEP_ALIVE) // wait a minute for thread
                .build()

        return JobManager(configuration)
    }
}
