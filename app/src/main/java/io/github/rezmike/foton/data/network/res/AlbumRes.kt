package io.github.rezmike.foton.data.network.res

data class AlbumRes(
        val id: String,
        val owner: String,
        val title: String,
        val preview: String,
        val description: String,
        val views: Int,
        val favorits: Int,
        val photocards: List<PhotoCardRes?>
)
