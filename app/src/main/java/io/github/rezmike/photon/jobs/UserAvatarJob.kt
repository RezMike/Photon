package io.github.rezmike.photon.jobs

import android.net.Uri
import com.birbit.android.jobqueue.Job
import com.birbit.android.jobqueue.Params
import com.birbit.android.jobqueue.RetryConstraint
import io.github.rezmike.photon.data.managers.DataManager
import io.github.rezmike.photon.data.storage.realm.UserRealm
import io.github.rezmike.photon.utils.AppConfig
import io.realm.Realm
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class UserAvatarJob(val avatarUrl: String) : Job(params) {

    private val dataManager = DataManager.INSTANCE
    private val userId = dataManager.getUserId()

    override fun onAdded() {
        val realm = Realm.getDefaultInstance()

        val userRealm = realm.where(UserRealm::class.java).equalTo("id", userId).findFirst()
        realm.executeTransaction { userRealm.avatar = avatarUrl }

        realm.close()
    }

    override fun onRun() {
        val photoFile = File(Uri.parse(avatarUrl).path)
        val requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), photoFile)
        val body = MultipartBody.Part.createFormData("avatar", photoFile.name, requestBody)

        dataManager.uploadUserAvatar(body)
                .subscribe({
                    val avatar = it.image
                    val realm = Realm.getDefaultInstance()

                    val userRealm = realm.where(UserRealm::class.java).equalTo("id", userId).findFirst()
                    realm.executeTransaction { userRealm.avatar = avatar }

                    realm.close()
                })
    }

    override fun shouldReRunOnThrowable(throwable: Throwable, runCount: Int, maxRunCount: Int): RetryConstraint {
        return RetryConstraint.createExponentialBackoff(runCount, AppConfig.JOB_INITIAL_BACK_OFF_IN_MS)
    }

    override fun onCancel(cancelReason: Int, throwable: Throwable?) {}

    companion object {
        private val params = Params(JobPriority.HIGH)
                .requireNetwork()
                .persist()
                .singleInstanceBy("Avatar")
    }
}