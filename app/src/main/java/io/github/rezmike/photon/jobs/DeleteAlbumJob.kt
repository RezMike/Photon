package io.github.rezmike.photon.jobs

import com.birbit.android.jobqueue.Job
import com.birbit.android.jobqueue.Params
import com.birbit.android.jobqueue.RetryConstraint
import io.github.rezmike.photon.data.managers.DataManager
import io.github.rezmike.photon.data.storage.realm.AlbumRealm
import io.github.rezmike.photon.utils.ConstantManager
import io.realm.Realm

class DeleteAlbumJob(val albumId: String) : Job(params) {

    override fun onAdded() {
        val realm = Realm.getDefaultInstance()

        val albumRealm = realm.where(AlbumRealm::class.java).equalTo("id", albumId).findFirst()

        realm.executeTransaction {
            if (!albumRealm.photoCards.isEmpty()) {
                albumRealm.photoCards.forEach { it.deleteFromRealm() }
            }
            albumRealm.deleteFromRealm()
        }

        realm.close()
    }

    override fun onRun() {
        DataManager.INSTANCE.deleteAlbumOnServer(albumId).subscribe()
    }

    override fun shouldReRunOnThrowable(throwable: Throwable, runCount: Int, maxRunCount: Int): RetryConstraint {
        return RetryConstraint.RETRY
    }

    override fun onCancel(cancelReason: Int, throwable: Throwable?) {}

    companion object {
        private val params = Params(JobPriority.HIGH)
                .requireNetwork()
                .persist()
                .groupBy(ConstantManager.JOB_GROUP_ALBUM)
    }
}