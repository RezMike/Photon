package io.github.rezmike.foton.ui.screens.photocard

import io.github.rezmike.foton.ui.abstracts.AbstractModel

class PhotocardModel : AbstractModel() {

    fun getUserData(userId: String) = dataManager.getUserSinFromNetwork(userId)

    fun saveFavorite(photoId: String) = dataManager.savePhotoFavoriteSin(photoId)

    fun downloadAndSavePhotoFile(photoUrl: String) = dataManager.getPhotoFileComplFromNetwork(photoUrl)
}