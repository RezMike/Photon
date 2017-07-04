package io.github.rezmike.foton.data.storage.realm

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class TagRealm() : RealmObject() {

    @PrimaryKey
    var id: String = ""
        private set
    var tag: String = ""
        private set

    constructor(tag: String) : this() {
        this.tag = tag
        this.id = tag
        // TODO: 13.06.2017 change id and add getters
    }
}