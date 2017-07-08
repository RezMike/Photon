package io.github.rezmike.foton.data.storage.realm

import io.github.rezmike.foton.data.network.res.AlbumRes
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class AlbumRealm() : RealmObject() {

    @PrimaryKey
    var id: String = ""
    var owner: String = ""
    var title: String = ""
    var description: String = ""
    var active: Boolean = false
    var isFavorite: Boolean = false
    var views: Int = 0
    var favorits: Int = 0
    var photoCards: RealmList<PhotoCardRealm> = RealmList()

    constructor(albumRes: AlbumRes) : this() {
        id = albumRes.id
        owner = albumRes.owner
        title = albumRes.title
        active = albumRes.active
        description = albumRes.description
        isFavorite = albumRes.isFavorite
        views = albumRes.views
        favorits = albumRes.favorits
    }
}