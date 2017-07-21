package io.github.rezmike.photon.ui.screens.profile

import io.github.rezmike.photon.data.storage.realm.UserRealm
import io.github.rezmike.photon.ui.screens.AbstractModel
import rx.Single

class ProfileModel : AbstractModel() {

    fun getProfileData(): Single<UserRealm> {
        val userId = dataManager.getUserId()!!
        return dataManager.getUserSinFromRealm(userId)
                .onErrorResumeNext { dataManager.getUserSinFromNetwork(userId) }
                .doOnSuccess { dataManager.getUserSinFromNetwork(userId) }
    }

    fun logoutUser() = dataManager.logoutUser()
}
