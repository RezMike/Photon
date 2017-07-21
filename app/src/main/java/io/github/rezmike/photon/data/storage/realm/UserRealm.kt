package io.github.rezmike.photon.data.storage.realm

import io.github.rezmike.photon.data.network.res.UserRes
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.io.Serializable

open class UserRealm() : RealmObject(), Serializable {

    @PrimaryKey
    var id: String = ""
    var name: String = ""
    var login: String = ""
    var avatar: String = ""
    var token: String = ""
    var albumCount: Int = 0
    var photocardCount: Int = 0
    var albums: RealmList<AlbumRealm> = RealmList()

    constructor(user: UserRes) : this() {
        id = user.id
        name = user.name
        login = user.login
        avatar = user.avatar
        token = user.token
        albumCount = user.albumCount
        photocardCount = user.photocardCount
    }
}