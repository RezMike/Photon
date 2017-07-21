package io.github.rezmike.photon.utils

import io.realm.RealmList
import io.realm.RealmModel

/**
 * Create and return new ArrayList, that contains all elements from this RealmList.
 */
fun <T : RealmModel> RealmList<T>.toArrayList(): ArrayList<T> {
    val arrayList = ArrayList<T>()
    arrayList.addAll(this)
    return arrayList
}
