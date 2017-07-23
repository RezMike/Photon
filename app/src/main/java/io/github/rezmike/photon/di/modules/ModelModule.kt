package io.github.rezmike.photon.di.modules

import com.birbit.android.jobqueue.JobManager
import com.birbit.android.jobqueue.config.Configuration
import dagger.Module
import dagger.Provides
import io.github.rezmike.photon.App
import io.github.rezmike.photon.data.managers.DataManager
import io.github.rezmike.photon.utils.JOB_KEEP_ALIVE
import io.github.rezmike.photon.utils.JOB_LOAD_FACTOR
import io.github.rezmike.photon.utils.JOB_MAX_CONSUMER_COUNT
import io.github.rezmike.photon.utils.JOB_MIN_CONSUMER_COUNT
import javax.inject.Singleton

@Module
class ModelModule {

    @Provides
    @Singleton
    fun provideDataManager(): DataManager = DataManager.INSTANCE

    @Provides
    @Singleton
    fun provideJobManager(): JobManager {

        val configuration = Configuration.Builder(App.context)
                .minConsumerCount(JOB_MIN_CONSUMER_COUNT) // always keep at least one consumer alive
                .maxConsumerCount(JOB_MAX_CONSUMER_COUNT) // up to 3 consumer at a time
                .loadFactor(JOB_LOAD_FACTOR) // 3 jobs per consumer
                .consumerKeepAlive(JOB_KEEP_ALIVE) // wait a minute for thread
                .build()

        return JobManager(configuration)
    }
}
