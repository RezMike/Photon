package io.github.rezmike.foton.data.network.transformers

import io.github.rezmike.foton.data.network.res.AlbumRes

class AlbumsCallTransformer : RestCallTransformer<List<AlbumRes>>() {
    override fun saveLastModify(lastModified: String) {
    }
}