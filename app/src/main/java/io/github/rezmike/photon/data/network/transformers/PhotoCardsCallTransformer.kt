package io.github.rezmike.photon.data.network.transformers

import io.github.rezmike.photon.data.managers.DataManager
import io.github.rezmike.photon.data.network.res.PhotoCardRes

class PhotoCardsCallTransformer : RestCallTransformer<List<PhotoCardRes>>() {
    override fun saveLastModify(lastModified: String) {
        DataManager.INSTANCE.preferencesManager.saveLastPhotoCardsUpdate(lastModified)
    }
}
