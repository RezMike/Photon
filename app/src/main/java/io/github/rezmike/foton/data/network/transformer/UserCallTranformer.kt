package io.github.rezmike.foton.data.network.transformer

import io.github.rezmike.foton.data.managers.DataManager
import io.github.rezmike.foton.data.network.res.UserRes

class UserCallTranformer(val userId: String) : RestCallTransformer<UserRes>() {
    override fun saveLastModify(lastModified: String) {
        DataManager.INSTANCE.preferencesManager.saveUserLastUpdate(userId, lastModified)
    }
}
