package io.github.rezmike.photon.data.network.transformers

import io.github.rezmike.photon.data.network.res.SuccessRes

class SuccessCallTransformer : RestCallTransformer<SuccessRes>() {
    override fun saveLastModify(lastModified: String) {
    }
}