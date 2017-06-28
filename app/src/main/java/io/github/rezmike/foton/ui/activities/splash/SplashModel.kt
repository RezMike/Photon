package io.github.rezmike.foton.ui.activities.splash

import io.github.rezmike.foton.ui.abstracts.AbstractModel
import io.github.rezmike.foton.utils.NetworkStatusChecker

class SplashModel : AbstractModel() {

    fun updateLocalDataCompl() = NetworkStatusChecker.isInternetAvailable()
            .toCompletable()
            .andThen(dataManager.getPhotoCardComplFromNetwork())
            .andThen(dataManager.getAlbumComplFromNetwork())
}