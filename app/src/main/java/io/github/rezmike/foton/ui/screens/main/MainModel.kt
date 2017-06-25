package io.github.rezmike.foton.ui.screens.main

import io.github.rezmike.foton.ui.abstracts.AbstractModel

class MainModel : AbstractModel(){

    fun getPhotoCardObs()  = dataManager.getPhotoCardObsFromRealm()

    fun  isAuth() = false //TODO сделать проверку
}