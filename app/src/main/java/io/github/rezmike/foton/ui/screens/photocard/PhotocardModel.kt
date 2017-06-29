package io.github.rezmike.foton.ui.screens.photocard

import io.github.rezmike.foton.data.storage.UserRealm
import io.github.rezmike.foton.ui.abstracts.AbstractModel
import rx.Completable

import rx.Single

class PhotocardModel : AbstractModel() {

    fun getAuthorPhoto(authorId: String): Single<UserRealm> {
        return dataManager.getUserObsFromNetwork(authorId)
    }


}