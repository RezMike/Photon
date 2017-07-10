package io.github.rezmike.photon.data.network.res

data class AlbumRes(
        val owner: String,
        val title: String,
        val description: String,
        val active: Boolean,
        val photocards: List<PhotoCardRes>,
        val isFavorite: Boolean,
        val id: String,
        val views: Int,
        val favorits: Int
)
