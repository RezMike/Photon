package io.github.rezmike.foton.data.network.transformer

import io.github.rezmike.foton.data.network.res.AlbumRes

class AlbumsRestCallTransformer : RestCallTransformer<List<AlbumRes>>(){
    override fun saveLastModify(lastModified: String) {
        TODO("not implemented")
    }
}