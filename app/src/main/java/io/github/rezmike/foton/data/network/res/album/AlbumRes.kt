package io.github.rezmike.foton.data.network.res.album

import io.github.rezmike.foton.data.network.res.photocard.PhotoCardRes

data class AlbumRes(
        val id: String ,
        val owner: String,
        val title: String,
        val preview: String,
        val description: String,
        val views: Int,
        val favorits: Int,
        val photocards: List<PhotoCardRes?>
)
