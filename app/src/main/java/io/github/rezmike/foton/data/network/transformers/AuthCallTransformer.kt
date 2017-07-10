package io.github.rezmike.foton.data.network.transformers

import io.github.rezmike.foton.data.network.res.UserRes

class AuthCallTransformer : RestCallTransformer<UserRes>() {
    override fun saveLastModify(lastModified: String) {
    }
}

