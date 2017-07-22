package io.github.rezmike.photon.jobs

import com.birbit.android.jobqueue.Job
import com.birbit.android.jobqueue.Params
import com.birbit.android.jobqueue.RetryConstraint
import io.github.rezmike.photon.data.managers.DataManager
import io.github.rezmike.photon.data.network.req.AlbumReq
import io.github.rezmike.photon.data.storage.realm.AlbumRealm
import io.realm.Realm

class EditAlbumJob(val albumId: String, val albumReq: AlbumReq) : Job(params) {

    override fun onAdded() {
        val realm = Realm.getDefaultInstance()

        val album = realm.where(AlbumRealm::class.java).equalTo("id", albumId).findFirst()

        realm.executeTransaction {
            album.title = albumReq.title
            album.description = albumReq.description
        }

        realm.close()
    }

    override fun onRun() {
        DataManager.INSTANCE.editAlbumOnServer(albumId, albumReq).subscribe()
    }

    override fun shouldReRunOnThrowable(throwable: Throwable, runCount: Int, maxRunCount: Int): RetryConstraint {
        return RetryConstraint.RETRY
    }

    override fun onCancel(cancelReason: Int, throwable: Throwable?) {}

    companion object {
        private val params = Params(JobPriority.HIGH)
                .requireNetwork()
                .persist()
                .singleInstanceBy("Album")
    }
}