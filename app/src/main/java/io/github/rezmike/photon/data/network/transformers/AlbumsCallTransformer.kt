package io.github.rezmike.photon.data.network.transformers

import io.github.rezmike.photon.data.network.res.AlbumRes

class AlbumsCallTransformer : RestCallTransformer<List<AlbumRes>>() {
    override fun saveLastModify(lastModified: String) {
    }
}