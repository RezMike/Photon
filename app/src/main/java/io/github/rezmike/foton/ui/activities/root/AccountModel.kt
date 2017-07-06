package io.github.rezmike.foton.ui.activities.root

import io.github.rezmike.foton.data.network.req.LoginReq
import io.github.rezmike.foton.ui.abstracts.AbstractModel

class AccountModel : AbstractModel() {

    fun isUserAuth() = dataManager.isUserAuth()

    fun login(loginReq: LoginReq) = dataManager.loginUserCompl(loginReq)
}