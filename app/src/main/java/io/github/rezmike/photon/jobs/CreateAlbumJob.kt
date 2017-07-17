package io.github.rezmike.photon.jobs

import com.birbit.android.jobqueue.Job
import com.birbit.android.jobqueue.Params
import com.birbit.android.jobqueue.RetryConstraint
import io.github.rezmike.photon.data.managers.DataManager
import io.github.rezmike.photon.data.network.req.AlbumReq
import io.github.rezmike.photon.data.storage.realm.AlbumRealm
import io.github.rezmike.photon.data.storage.realm.UserRealm
import io.realm.Realm
import java.util.*

class CreateAlbumJob(val albumReq: AlbumReq) : Job(params) {

    private val albumId = Date().toString()

    override fun onAdded() {
        val realm = Realm.getDefaultInstance()

        val userRealm = realm.where(UserRealm::class.java).equalTo("id", albumReq.owner).findFirst()
        val albumRealm = AlbumRealm(albumId, albumReq)
        realm.executeTransaction { userRealm.albums.add(albumRealm) }

        realm.close()
    }

    override fun onRun() {
        DataManager.INSTANCE.createAlbumOnServer(albumReq)
                .subscribe({
                    val realm = Realm.getDefaultInstance()

                    val serverAlbumRealm = AlbumRealm(it)
                    val localAlbumRealm = realm.where(AlbumRealm::class.java)
                            .equalTo("id", albumId)
                            .findFirst()
                    val userRealm = realm.where(UserRealm::class.java)
                            .equalTo("id", albumReq.owner)
                            .findFirst()

                    realm.executeTransaction {
                        if (localAlbumRealm.isValid) localAlbumRealm.deleteFromRealm()
                        userRealm.albums.add(serverAlbumRealm)
                    }
                    realm.close()
                })
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