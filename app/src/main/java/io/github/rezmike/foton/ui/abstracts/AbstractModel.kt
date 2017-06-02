package io.github.rezmike.foton.ui.abstracts

import android.support.annotation.VisibleForTesting

import com.birbit.android.jobqueue.JobManager

import javax.inject.Inject

import io.github.rezmike.foton.data.managers.DataManager
import io.github.rezmike.foton.di.components.ModelComponent
import io.github.rezmike.foton.di.modules.ModelModule
import io.github.rezmike.foton.utils.DaggerService

import android.support.annotation.VisibleForTesting.NONE
import io.github.rezmike.foton.di.components.DaggerModelComponent

abstract class AbstractModel {

    @Inject
    lateinit var mDataManager: DataManager
    @Inject
    lateinit var mJobManager: JobManager

    constructor() {
        var component = DaggerService.getComponent(ModelComponent::class)
        if (component == null) {
            component = DaggerModelComponent.builder()
                    .modelModule(ModelModule())
                    .build()
            DaggerService.registerComponent(ModelComponent::class, component)
        }
        component?.inject(this)
    }

    @VisibleForTesting(otherwise = NONE)
    constructor(dataManager: DataManager, jobManager: JobManager) {
        mDataManager = dataManager
        mJobManager = jobManager
    }
}
