package io.github.rezmike.photon.ui.screens.user

import io.github.rezmike.photon.data.storage.realm.UserRealm
import io.github.rezmike.photon.ui.screens.AbstractModel
import rx.Single

class UserModel : AbstractModel() {

    fun getUserData(userId: String): Single<UserRealm> {
        return dataManager.getUserSinFromRealm(userId)
                .onErrorResumeNext { dataManager.getUserSinFromNetwork(userId) }
                .doOnSuccess { dataManager.getUserSinFromNetwork(userId).subscribe({}, {}) }
    }
}