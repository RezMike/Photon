package io.github.rezmike.foton.ui.screens.photocard

import io.github.rezmike.foton.data.storage.UserRealm
import io.github.rezmike.foton.ui.abstracts.AbstractModel
import rx.Single

class PhotocardModel : AbstractModel() {

    fun getUserData(userId: String): Single<UserRealm> {
        return dataManager.getUserSingleFromRealm(userId)
                .onErrorResumeNext { dataManager.getUserSinFromNetwork(userId) }
                .doOnSuccess { dataManager.getUserSinFromNetwork(userId) }
    }


    fun saveFavorite(photoId: String) = dataManager.savePhotoFavoriteSin(photoId)

    fun downloadAndSavePhotoFile(photoUrl: String) = dataManager.getPhotoFileComplFromNetwork(photoUrl)
}