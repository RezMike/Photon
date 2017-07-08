package io.github.rezmike.foton.ui.screens.photocard

import io.github.rezmike.foton.data.storage.realm.UserRealm
import io.github.rezmike.foton.ui.abstracts.AbstractModel
import rx.Single

class PhotocardModel : AbstractModel() {

    fun getUserData(userId: String): Single<UserRealm> {
        return dataManager.getUserSinFromRealm(userId)
                .onErrorResumeNext { dataManager.getUserSinFromNetwork(userId) }
                .doOnSuccess { dataManager.getUserSinFromNetwork(userId) }
    }

    fun saveFavorite(photoId: String) = dataManager.savePhotoCardFavoriteComplToRealm(photoId)
            .andThen(dataManager.savePhotoCardFavoriteComplToNetwork(photoId))

    fun downloadAndSavePhotoFile(photoUrl: String) = dataManager.getPhotoFileComplFromNetwork(photoUrl)
}