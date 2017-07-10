package io.github.rezmike.photon.data.network.transformers

import io.github.rezmike.photon.data.network.res.UserRes

class AuthCallTransformer : RestCallTransformer<UserRes>() {
    override fun saveLastModify(lastModified: String) {
    }
}

