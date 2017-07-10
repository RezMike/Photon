package io.github.rezmike.photon.data.network.transformers

import io.github.rezmike.photon.data.managers.DataManager
import io.github.rezmike.photon.data.network.res.UserRes

class UserCallTranformer(val userId: String) : RestCallTransformer<UserRes>() {
    override fun saveLastModify(lastModified: String) {
        DataManager.INSTANCE.preferencesManager.saveUserLastUpdate(userId, lastModified)
    }
}
