package io.github.rezmike.foton.ui.abstracts

import android.support.annotation.VisibleForTesting

import com.birbit.android.jobqueue.JobManager

import javax.inject.Inject

import io.github.rezmike.foton.data.managers.DataManager
import io.github.rezmike.foton.di.components.ModelComponent
import io.github.rezmike.foton.di.modules.ModelModule

import android.support.annotation.VisibleForTesting.NONE
import io.github.rezmike.foton.di.components.DaggerModelComponent

abstract class AbstractModel {

    @Inject
    lateinit var mDataManager: DataManager
    @Inject
    lateinit var mJobManager: JobManager


    @Suppress("LeakingThis")
    constructor() {
        DaggerModelComponent.builder()
                .modelModule(ModelModule())
                .build()
                .inject(this)
    }


    @VisibleForTesting(otherwise = NONE)
    constructor(dataManager: DataManager, jobManager: JobManager) {
        mDataManager = dataManager
        mJobManager = jobManager
    }
}
