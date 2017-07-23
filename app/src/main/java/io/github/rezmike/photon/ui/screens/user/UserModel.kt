package io.github.rezmike.photon.ui.screens.user

import io.github.rezmike.photon.data.storage.realm.UserRealm
import io.github.rezmike.photon.ui.screens.AbstractModel
import rx.Single

class UserModel : AbstractModel(){
    fun getUserDate(userId: String): Single<UserRealm> {
        TODO()
    }
}