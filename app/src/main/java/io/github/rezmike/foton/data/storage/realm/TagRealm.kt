package io.github.rezmike.foton.data.storage.realm

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class TagRealm() : RealmObject() {

    @PrimaryKey
    var tag: String = ""
        private set

    constructor(tag: String) : this() {
        this.tag = tag
    }
}