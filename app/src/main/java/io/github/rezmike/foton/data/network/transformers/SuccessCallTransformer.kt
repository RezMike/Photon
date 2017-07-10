package io.github.rezmike.foton.data.network.transformers

import io.github.rezmike.foton.data.network.res.SuccessRes

class SuccessCallTransformer : RestCallTransformer<SuccessRes>() {
    override fun saveLastModify(lastModified: String) {
    }
}