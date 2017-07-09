package io.github.rezmike.foton.data.network.transformers

import io.github.rezmike.foton.data.managers.DataManager
import io.github.rezmike.foton.data.network.res.PhotoCardRes

class PhotoCardsCallTransformer : RestCallTransformer<List<PhotoCardRes>>() {
    override fun saveLastModify(lastModified: String) {
        DataManager.INSTANCE.preferencesManager.saveLastPhotoCardsUpdate(lastModified)
    }
}
