package io.github.rezmike.photon.data.storage.realm

import io.github.rezmike.photon.data.network.res.Filters
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class FilterRealm() : RealmObject() {

    @PrimaryKey
    var id: String = ""
        private set
    var dish: String = ""
        private set
    var nuances: String = ""
        private set
    var decor: String = ""
        private set
    var temperature: String = ""
        private set
    var light: String = ""
        private set
    var lightDirection: String = ""
        private set
    var lightSource: String = ""
        private set

    constructor(filters: Filters) : this() {
        dish = filters.dish
        nuances = filters.nuances
        decor = filters.decor
        temperature = filters.temperature
        light = filters.light
        lightDirection = filters.lightDirection
        lightSource = filters.lightSource
    }
}