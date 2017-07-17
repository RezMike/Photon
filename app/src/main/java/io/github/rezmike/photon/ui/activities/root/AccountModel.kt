package io.github.rezmike.photon.ui.activities.root

import io.github.rezmike.photon.data.network.req.LoginReq
import io.github.rezmike.photon.jobs.UserAvatarJob
import io.github.rezmike.photon.ui.abstracts.AbstractModel

class AccountModel : AbstractModel() {

    fun isUserAuth() = dataManager.isUserAuth()

    fun login(loginReq: LoginReq) = dataManager.loginUserCompl(loginReq)

    fun uploadAvatarToServer(avatarUrl: String) {
        jobManager.addJobInBackground(UserAvatarJob(avatarUrl))
    }
}