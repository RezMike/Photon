package io.github.rezmike.foton.data.network.res.user

import io.github.rezmike.foton.data.network.res.album.AlbumRes

data class UserRes(
        val id: String,
        val name: String,
        val login: String,
        val mail: String,
        val avatar: String,
        val token: String,
        val albumCount: Int,
        val photocardCount: Int,
        val albums: List<AlbumRes?>
)
