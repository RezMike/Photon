package io.github.rezmike.foton.data.storage

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class AlbumRealm() : RealmObject() {

    @PrimaryKey
    var id: String = ""
    var owner: String = ""
    var title: String = ""
    var preview: String = ""
    var description: String = ""
    var views: Int = 0
    var favorits: Int = 0
    var photoCards: RealmList<PhotoCardRealm> = RealmList()
}