package io.github.rezmike.photon.ui.activities.root

import io.github.rezmike.photon.data.network.req.AlbumReq
import io.github.rezmike.photon.data.network.req.LoginReq
import io.github.rezmike.photon.jobs.AvatarUserJob
import io.github.rezmike.photon.jobs.CreateAlbumJob
import io.github.rezmike.photon.ui.abstracts.AbstractModel

class AccountModel : AbstractModel() {

    fun isUserAuth() = dataManager.isUserAuth()

    fun login(email: String, password: String) = dataManager.loginUserCompl(LoginReq(email, password))

    fun uploadAvatarOnServer(avatarUrl: String) {
        jobManager.addJobInBackground(AvatarUserJob(avatarUrl))
    }

    fun createAlbum(title: String, description: String) {
        jobManager.addJobInBackground(CreateAlbumJob(AlbumReq(dataManager.getUserId()!!, title, description)))
    }
}