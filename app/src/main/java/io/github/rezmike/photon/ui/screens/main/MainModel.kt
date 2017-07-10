package io.github.rezmike.photon.ui.screens.main

import io.github.rezmike.photon.ui.abstracts.AbstractModel

class MainModel : AbstractModel(){

    fun getPhotoCardObs() = dataManager.getPhotoCardObsFromRealm()

    fun logoutUser() {
        dataManager.logoutUser()
    }
}