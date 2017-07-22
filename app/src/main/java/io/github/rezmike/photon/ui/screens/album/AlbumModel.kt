package io.github.rezmike.photon.ui.screens.album

import io.github.rezmike.photon.ui.screens.AbstractModel
import rx.Completable

class AlbumModel : AbstractModel() {

    fun getUserId() = dataManager.getUserId()

    fun deleteAlbum(albumId: String): Completable {
        return dataManager.deleteAlbumOnServer(albumId)
                .doOnCompleted { dataManager.deleteAlbumFromRealm(albumId) }
    }
}