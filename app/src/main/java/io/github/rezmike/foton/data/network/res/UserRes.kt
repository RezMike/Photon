package io.github.rezmike.foton.data.network.res

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
