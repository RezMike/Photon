package io.github.rezmike.foton.data.storage

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class PhotoCardRealm() : RealmObject() {

    @PrimaryKey
    var id: String = ""
    var owner: String = ""
    var title: String = ""
    var photo: String = ""
    var views: Int = 0
    var favorits: Int = 0
    var tags: RealmList<TagRealm> = RealmList()
    var filters : FilterRealm = FilterRealm()
}