package io.github.rezmike.foton.ui.abstracts

import android.support.annotation.VisibleForTesting
import android.support.annotation.VisibleForTesting.NONE
import com.birbit.android.jobqueue.JobManager
import io.github.rezmike.foton.data.managers.DataManager
import io.github.rezmike.foton.di.components.DaggerModelComponent
import io.github.rezmike.foton.di.modules.ModelModule
import javax.inject.Inject

abstract class AbstractModel {

    @Inject
    lateinit var dataManager: DataManager
    @Inject
    lateinit var jobManager: JobManager

    @Suppress("LeakingThis")
    constructor() {
        DaggerModelComponent.builder()
                .modelModule(ModelModule())
                .build()
                .inject(this)
    }

    @VisibleForTesting(otherwise = NONE)
    constructor(dataManager: DataManager, jobManager: JobManager) {
        this.dataManager = dataManager
        this.jobManager = jobManager
    }
}
