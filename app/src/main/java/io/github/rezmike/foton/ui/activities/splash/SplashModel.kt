package io.github.rezmike.foton.ui.activities.splash

import io.github.rezmike.foton.ui.abstracts.AbstractModel

class SplashModel : AbstractModel(){
    fun updateLocalDataCompl() = mDataManager.startUpdateLocalData()
}