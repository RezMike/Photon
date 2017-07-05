package io.github.rezmike.foton.ui.screens.photocard

import io.github.rezmike.foton.ui.abstracts.AbstractModel

class PhotocardModel : AbstractModel() {

    fun getUserData(userId: String) = dataManager.getUserObsFromNetwork(userId)

    fun saveOnFavorite(photoId: String) = dataManager.savePhotoOnFavoriteSing(photoId)

    fun downloadAndSavePhotoFile(photoUrl: String) = dataManager.getPhotoFileComplFromNetwork(photoUrl)
}