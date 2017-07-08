package io.github.rezmike.foton.data.network.transformer

import io.github.rezmike.foton.data.network.res.SuccessRes

class SuccessCallTransformer : RestCallTransformer<SuccessRes>() {
    override fun saveLastModify(lastModified: String) {
    }
}