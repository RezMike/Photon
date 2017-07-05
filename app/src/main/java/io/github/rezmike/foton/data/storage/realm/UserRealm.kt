package io.github.rezmike.foton.data.storage.realm

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
}



