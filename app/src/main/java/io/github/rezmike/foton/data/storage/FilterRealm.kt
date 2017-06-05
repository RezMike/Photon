package io.github.rezmike.foton.data.storage

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class FilterRealm() : RealmObject() {

    @PrimaryKey
    var id: String = ""
    var dish: String = ""
    var nuances: String = ""
    var decor: String = ""
    var temperature: String = ""
    var light: String = ""
    var lightDirection: String = ""
    var lightSource: String = ""
}