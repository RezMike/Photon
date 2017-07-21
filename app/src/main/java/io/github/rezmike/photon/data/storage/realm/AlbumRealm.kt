package io.github.rezmike.photon.data.storage.realm

import io.github.rezmike.photon.data.network.req.AlbumReq
import io.github.rezmike.photon.data.network.res.AlbumRes
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.io.Serializable

open class AlbumRealm() : RealmObject(), Serializable {

    @PrimaryKey
    var id: String = ""
    var owner: String = ""
    var title: String = ""
    var description: String = ""
    var isFavorite: Boolean = false
    var views: Int = 0
    var favorits: Int = 0
    var photoCards: RealmList<PhotoCardRealm> = RealmList()

    constructor(albumRes: AlbumRes) : this() {
        id = albumRes.id
        owner = albumRes.owner
        title = albumRes.title
        description = albumRes.description
        isFavorite = albumRes.isFavorite
        views = albumRes.views
        favorits = albumRes.favorits
    }

    constructor(id: String, albumReq: AlbumReq) : this() {
        this.id = id
        this.owner = albumReq.owner
        this.title = albumReq.title
        this.description = albumReq.description
    }
}