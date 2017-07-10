package io.github.rezmike.foton.data.network.res

data class PhotoCardRes(
        val owner: String,
        val title: String,
        val photo: String,
        val active: Boolean,
        val updated: String,
        val created: String,
        val filters: Filters,
        val tags: List<String>,
        val favorits: Int,
        val views: Int,
        val id: String
)