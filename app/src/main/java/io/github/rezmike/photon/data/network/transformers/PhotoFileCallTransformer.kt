package io.github.rezmike.photon.data.network.transformers

import okhttp3.ResponseBody

class PhotoFileCallTransformer : RestCallTransformer<ResponseBody>() {
    override fun saveLastModify(lastModified: String) {
    }
}
