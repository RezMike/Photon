package io.github.rezmike.foton.data.network.transformer

import okhttp3.ResponseBody

class PhotoFileCallTransformer : RestCallTransformer<ResponseBody>() {
    override fun saveLastModify(lastModified: String) {
    }
}
