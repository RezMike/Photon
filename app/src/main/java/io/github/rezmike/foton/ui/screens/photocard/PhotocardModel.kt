package io.github.rezmike.foton.ui.screens.photocard

import io.github.rezmike.foton.data.storage.UserRealm
import io.github.rezmike.foton.ui.abstracts.AbstractModel
import rx.Single
import java.io.File

class PhotocardModel : AbstractModel() {

    fun getAuthorPhoto(authorId: String): Single<UserRealm> {
        return dataManager.getUserObsFromNetwork(authorId)
    }

    fun saveOnFavorite(photoId: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun savePhotoOnExternalStorage(photo: String): Single<File> {
        TODO()
    }


}