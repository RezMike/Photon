package io.github.rezmike.photon.data.network.req

import java.io.Serializable

data class AlbumReq(val owner: String, val title: String, val description: String) : Serializable