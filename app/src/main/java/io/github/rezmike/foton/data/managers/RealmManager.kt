package io.github.rezmike.foton.data.managers

import io.github.rezmike.foton.data.network.res.AlbumRes
import io.github.rezmike.foton.data.network.res.PhotoCardRes
import io.github.rezmike.foton.data.network.res.UserRes
import io.github.rezmike.foton.data.storage.AlbumRealm
import io.github.rezmike.foton.data.storage.PhotoCardRealm
import io.github.rezmike.foton.data.storage.TagRealm
import io.github.rezmike.foton.data.storage.UserRealm
import io.realm.Realm
import io.realm.RealmObject
import rx.Observable
import rx.Single

class RealmManager {

    private var realmInstance: Realm? = null

    //region ======================== User ========================

    fun getUser(userId: String): Single<UserRealm> {
        val realm = Realm.getDefaultInstance()

        val user = realm.where(UserRealm::class.java).equalTo("id", userId).findFirst()

        return if (user == null) Single.error(Throwable("User with id \"$userId\" not found"))
        else return Single.just(user)
    }

    fun saveUserResponseToRealm(user: UserRes): UserRealm {
        val realm = Realm.getDefaultInstance()

        val userRealm = UserRealm(user)

        if (!user.albums.isEmpty()) {
            Observable.from(user.albums)
                    .map { saveAlbumResponseToRealm(it) }
                    .subscribe { userRealm.albums.add(it) }
        }

        realm.executeTransaction { it.insertOrUpdate(userRealm) }
        realm.close()

        return userRealm
    }

    //endregion

    //region ======================== Albums ========================

    fun saveAlbumResponseToRealm(albumRes: AlbumRes): AlbumRealm {
        val realm = Realm.getDefaultInstance()

        val albumRealm = AlbumRealm(albumRes)

        if (!albumRes.photocards.isEmpty()) {
            Observable.from(albumRes.photocards)
                    .map { savePhotoCardResponseToRealm(it) }
                    .subscribe { albumRealm.photoCards.add(it) }
        }

        realm.executeTransaction { it.insertOrUpdate(albumRealm) }
        realm.close()

        return albumRealm
    }

    //endregion

    //region ======================== PhotoCards ========================

    fun getAllPhotoCards(): Observable<PhotoCardRealm> {
        val managedPhotoCards = getQueryRealmInstance().where(PhotoCardRealm::class.java).findAllAsync()
        return managedPhotoCards
                .asObservable()
                .filter { it.isLoaded }
                .flatMap { Observable.from(it) }
    }

    fun savePhotoCardResponseToRealm(photoCardRes: PhotoCardRes): PhotoCardRealm {
        val realm = Realm.getDefaultInstance()

        val photoCardRealm = PhotoCardRealm(photoCardRes)

        if (!photoCardRes.tags.isEmpty()) {
            Observable.from(photoCardRes.tags)
                    .map { saveTagToRealm(it) }
                    .subscribe { photoCardRealm.tags.add(it) }
        }

        realm.executeTransaction { it.insertOrUpdate(photoCardRealm) }
        realm.close()

        return photoCardRealm
    }

    fun saveTagToRealm(tag: String): TagRealm {
        val realm = Realm.getDefaultInstance()

        val tagRealm = TagRealm(tag)

        realm.executeTransaction { it.insertOrUpdate(tagRealm) }
        realm.close()

        return tagRealm
    }

    //endregion

    fun <T : RealmObject> deleteFromRealm(entityRealmClass: Class<T>, id: String) {
        val realm = Realm.getDefaultInstance()
        val entity = realm.where<T>(entityRealmClass).equalTo("id", id).findFirst()

        if (entity != null) {
            realm.executeTransaction { entity.deleteFromRealm() }
        }
        realm.close()
    }

    private fun getQueryRealmInstance(): Realm {
        if (realmInstance == null || realmInstance!!.isClosed()) {
            realmInstance = Realm.getDefaultInstance()
        }
        return realmInstance!!
    }
}