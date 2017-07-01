package io.github.rezmike.foton.ui.screens.photocard

import io.github.rezmike.foton.ui.abstracts.AbstractModel

class PhotocardModel : AbstractModel() {

    fun getUserData(userId: String) = dataManager.getUserObsFromNetwork(userId)

    fun saveOnFavorite(photoId: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun downloadAndSavePhotoFile(photoUrl: String) = dataManager.getPhotoFileComplFromNetwork(photoUrl)
}