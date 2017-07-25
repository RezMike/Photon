package io.github.rezmike.photon.ui.screens.add_info

import io.github.rezmike.photon.data.storage.realm.AlbumRealm
import io.github.rezmike.photon.ui.screens.AbstractModel
import io.github.rezmike.photon.utils.toArrayList
import rx.Observable

class AddInfoModel : AbstractModel() {
    fun getAlbumList(): Observable<ArrayList<AlbumRealm>> {
        return dataManager.getUserSinFromRealm(dataManager.getUserId()!!)
                .map { it.albums.filter { !it.isFavorite }.toCollection(ArrayList()) }
                .toObservable()
    }
}