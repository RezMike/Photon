package io.github.rezmike.foton.data.network.res

data class PhotoCardRes(
        val id: String,
        val owner: String,
        val title: String,
        val photo: String,
        val views: Int,
        val favorits: Int,
        val tags: List<String>,
        val filters: Filters
)