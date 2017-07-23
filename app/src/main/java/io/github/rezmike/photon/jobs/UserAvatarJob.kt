package io.github.rezmike.photon.jobs

import android.net.Uri
import com.birbit.android.jobqueue.Job
import com.birbit.android.jobqueue.Params
import com.birbit.android.jobqueue.RetryConstraint
import io.github.rezmike.photon.data.managers.DataManager
import io.github.rezmike.photon.data.network.req.EditProfileReq
import io.github.rezmike.photon.data.storage.realm.UserRealm
import io.github.rezmike.photon.utils.AppConfig
import io.github.rezmike.photon.utils.ConstantManager
import io.realm.Realm
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class UserAvatarJob(val avatarFile: File) : Job(params) {

    private val userId = DataManager.INSTANCE.getUserId()!!

    override fun onAdded() {
        val avatarUrl = Uri.fromFile(avatarFile).toString()

        val realm = Realm.getDefaultInstance()

        val userRealm = realm.where(UserRealm::class.java).equalTo("id", userId).findFirst()
        realm.executeTransaction { userRealm.avatar = avatarUrl }

        realm.close()

        DataManager.INSTANCE.saveUserAvatar(avatarUrl)
    }

    override fun onRun() {
        val requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), avatarFile)
        val body = MultipartBody.Part.createFormData("image", avatarFile.name, requestBody)

        DataManager.INSTANCE.uploadImageToServer(body)
                .doOnSuccess { DataManager.INSTANCE.updateProfileInfo(EditProfileReq(avatar = it.image)).subscribe() }
                .subscribe()
    }

    override fun shouldReRunOnThrowable(throwable: Throwable, runCount: Int, maxRunCount: Int): RetryConstraint {
        return RetryConstraint.createExponentialBackoff(runCount, AppConfig.JOB_INITIAL_BACK_OFF_IN_MS)
    }

    override fun onCancel(cancelReason: Int, throwable: Throwable?) {}

    companion object {
        private val params = Params(JobPriority.MID)
                .requireNetwork()
                .persist()
                .singleInstanceBy(ConstantManager.JOB_GROUP_AVATAR)
    }
}