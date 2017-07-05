package io.github.rezmike.foton.data.storage

import io.github.rezmike.foton.data.network.res.UserRes
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class UserRealm() : RealmObject() {

    @PrimaryKey
    var id: String = ""
    var name: String = ""
    var login: String = ""
    var mail: String = ""
    var avatar: String = ""
    var token: String = ""
    var albumCount: Int = 0
    var photocardCount: Int = 0
    var list: RealmList<AlbumRealm> = RealmList()

    constructor(user: UserRes) : this() {
        id = user.id
        name = user.name
        login = user.login
        mail = user.mail
        avatar = user.avatar
        token = user.token
        albumCount = user.albumCount
        photocardCount = user.photocardCount
    }
}