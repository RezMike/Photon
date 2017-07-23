package io.github.rezmike.photon.ui.screens.album

import io.github.rezmike.photon.jobs.DeleteAlbumJob
import io.github.rezmike.photon.ui.screens.AbstractModel

class AlbumModel : AbstractModel() {

    fun getUserId() = dataManager.getUserId()

    fun deleteAlbum(albumId: String) {
        jobManager.addJobInBackground(DeleteAlbumJob(albumId))
    }
}