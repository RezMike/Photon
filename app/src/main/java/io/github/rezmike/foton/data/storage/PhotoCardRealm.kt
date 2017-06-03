package io.github.rezmike.foton.data.storage

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

class PhotoCardRealm : RealmObject {
    constructor() {}

    @PrimaryKey
    var id: String = ""
    var owner: String = ""
    var title: String = ""
    var photo: String = ""
    var views: Int = 0
    var favorits: Int = 0
    var tags: ArrayList<String> = ArrayList()
    var filters : FilterRealm = FilterRealm()
}