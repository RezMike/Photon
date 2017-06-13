package io.github.rezmike.foton.ui.screens.splash

import io.github.rezmike.foton.ui.abstracts.AbstractModel

class SplashModel : AbstractModel() {

    fun updateLocalDataObs() = mDataManager.startUpdateLocalData()
}
