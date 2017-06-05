package io.github.rezmike.foton.data.storage

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class TagRealm() : RealmObject() {

    @PrimaryKey
    var id : String = ""
    var tag : String = ""
}