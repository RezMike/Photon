package io.github.rezmike.foton.data.network.res

data class AlbumRes(
        val owner: String,
        val title: String,
        val description: String,
        val photocards: List<PhotoCardRes>,
        val isFavorite: Boolean,
        val id: String,
        val views: Int,
        val favorits: Int
)
