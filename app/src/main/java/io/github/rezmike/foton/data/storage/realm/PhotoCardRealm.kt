package io.github.rezmike.foton.data.storage.realm

import io.github.rezmike.foton.data.network.res.PhotoCardRes
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class PhotoCardRealm() : RealmObject() {

    @PrimaryKey
    var id: String = ""
        private set
    var owner: String = ""
        private set
    var title: String = ""
        private set
    var photo: String = ""
        private set
    var created: String = ""
        private set
    var updated: String = ""
        private set
    var views: Int = 0
        private set
    var favorits: Int = 0
    var tags: RealmList<TagRealm> = RealmList()
        private set
    var filters: FilterRealm = FilterRealm()
        private set

    constructor(photoCardRes: PhotoCardRes) : this() {
        id = photoCardRes.id
        owner = photoCardRes.owner
        title = photoCardRes.title
        photo = photoCardRes.photo
        created = photoCardRes.created
        updated = photoCardRes.updated
        views = photoCardRes.views
        favorits = photoCardRes.favorits
        filters = FilterRealm(photoCardRes.filters)
        if (!photoCardRes.tags.isEmpty()) {
            photoCardRes.tags
                    .map { TagRealm(it) }
                    .forEach { tags.add(it) }
        }
    }
}