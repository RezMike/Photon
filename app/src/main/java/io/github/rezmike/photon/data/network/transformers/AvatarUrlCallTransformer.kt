package io.github.rezmike.photon.data.network.transformers

import io.github.rezmike.photon.data.network.res.AvatarUrlRes

class AvatarUrlCallTransformer : RestCallTransformer<AvatarUrlRes>() {
    override fun saveLastModify(lastModified: String) {
    }
}