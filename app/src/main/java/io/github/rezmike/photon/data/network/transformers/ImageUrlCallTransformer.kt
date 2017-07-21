package io.github.rezmike.photon.data.network.transformers

import io.github.rezmike.photon.data.network.res.ImageUrlRes

class ImageUrlCallTransformer : RestCallTransformer<ImageUrlRes>() {
    override fun saveLastModify(lastModified: String) {
    }
}