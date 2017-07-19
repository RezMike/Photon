package io.github.rezmike.photon.ui.activities.splash

import io.github.rezmike.photon.ui.screens.AbstractModel
import io.github.rezmike.photon.utils.NetworkStatusChecker

class SplashModel : AbstractModel() {

    fun updateLocalDataCompl() = NetworkStatusChecker.isInternetAvailable()
            .toCompletable()
            .andThen(dataManager.getPhotoCardComplFromNetwork())
            .andThen(dataManager.getAlbumComplFromNetwork())
}