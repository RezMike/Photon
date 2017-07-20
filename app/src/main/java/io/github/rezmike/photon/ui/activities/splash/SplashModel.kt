package io.github.rezmike.photon.ui.activities.splash

import io.github.rezmike.photon.ui.screens.AbstractModel
import io.github.rezmike.photon.utils.NetworkStatusChecker
import rx.Completable

class SplashModel : AbstractModel() {

    fun updateLocalDataCompl(): Completable {
        return NetworkStatusChecker.isInternetAvailable()
                .toCompletable()
                .andThen(dataManager.getPhotoCardComplFromNetwork())
                .andThen(dataManager.getAlbumComplFromNetwork())
    }
}