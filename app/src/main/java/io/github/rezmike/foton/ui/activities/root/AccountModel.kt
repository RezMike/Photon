package io.github.rezmike.foton.ui.activities.root

import io.github.rezmike.foton.data.network.req.SingInReq
import io.github.rezmike.foton.ui.abstracts.AbstractModel

class AccountModel : AbstractModel() {

    fun isUserAuth() = dataManager.isUserAuth()

    fun signIn(signInReq: SingInReq) = dataManager.signIn(signInReq)
}