package io.github.rezmike.photon.data.network.res

data class UserRes(
        val id: String,
        val name: String,
        val login: String,
        val avatar: String,
        val token: String,
        val albumCount: Int,
        val photocardCount: Int,
        val albums: List<AlbumRes>
)
