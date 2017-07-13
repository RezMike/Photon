package io.github.rezmike.photon.data.storage.realm

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.io.Serializable

open class TagRealm() : RealmObject(), Serializable {

    @PrimaryKey
    var tag: String = ""
        private set

    constructor(tag: String) : this() {
        this.tag = tag
    }
}