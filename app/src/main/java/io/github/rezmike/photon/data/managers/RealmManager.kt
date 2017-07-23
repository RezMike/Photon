package io.github.rezmike.photon.data.managers

import io.github.rezmike.photon.data.network.res.AlbumRes
import io.github.rezmike.photon.data.network.res.PhotoCardRes
import io.github.rezmike.photon.data.network.res.UserRes
import io.github.rezmike.photon.data.storage.realm.AlbumRealm
import io.github.rezmike.photon.data.storage.realm.PhotoCardRealm
import io.github.rezmike.photon.data.storage.realm.UserRealm
import io.realm.Realm
import io.realm.RealmObject
import rx.Observable
import rx.Single

class RealmManager {

    private var realmInstance: Realm? = null

    //region ======================== User ========================

    fun getUser(userId: String): Single<UserRealm> {

        val user = getQueryRealmInstance()
                .where(UserRealm::class.java)
                .equalTo("id", userId)
                .findFirst()

        if (user == null)
            return Single.error(Throwable("User with id \"$userId\" not found"))
        else
            return Single.just(user)
    }

    fun saveUserResponseToRealm(user: UserRes) {
        val realm = Realm.getDefaultInstance()

        val userRealm = UserRealm(user)

        if (!user.albums.isEmpty()) {
            user.albums
                    .onEach { if (!it.active) deleteAlbumFromRealm(it.id) }
                    .filter { it.active }
                    .map { createAlbumRealm(it) }
                    .forEach { userRealm.albums.add(it) }
        }

        realm.executeTransaction { it.insertOrUpdate(userRealm) }
        realm.close()
    }

    fun saveAndGetUserRealm(user: UserRes): UserRealm {
        val realm = getQueryRealmInstance()
        realm.beginTransaction()

        val userRealm = UserRealm(user)

        if (!user.albums.isEmpty()) {
            user.albums
                    .onEach { if (!it.active) deleteAlbumFromRealm(it.id) }
                    .filter { it.active }
                    .map { createAlbumRealm(it) }
                    .forEach { userRealm.albums.add(it) }
        }

        val managedUserRealm = realm.copyToRealmOrUpdate(userRealm)

        realm.commitTransaction()

        return managedUserRealm
    }

    //endregion

    //region ======================== Albums ========================

    fun saveAlbumResponseToRealm(albumRes: AlbumRes) {
        val realm = Realm.getDefaultInstance()

        val albumRealm = createAlbumRealm(albumRes)

        realm.executeTransaction { it.insertOrUpdate(albumRealm) }
        realm.close()
    }

    private fun createAlbumRealm(albumRes: AlbumRes): AlbumRealm {
        val albumRealm = AlbumRealm(albumRes)

        if (!albumRes.photocards.isEmpty()) {
            albumRes.photocards
                    .onEach { if (!it.active) deleteFromRealm(PhotoCardRealm::class.java, it.id) }
                    .filter { it.active }
                    .map { PhotoCardRealm(it) }
                    .forEach { albumRealm.photoCards.add(it) }
        }

        return albumRealm
    }

    fun deleteAlbumFromRealm(albumId: String) {
        val realm = Realm.getDefaultInstance()

        val albumRealm = realm.where<AlbumRealm>(AlbumRealm::class.java).equalTo("id", albumId).findFirst() ?: return

        realm.executeTransaction {
            if (!albumRealm.photoCards.isEmpty()) {
                albumRealm.photoCards.forEach { it.deleteFromRealm() }
            }
            albumRealm.deleteFromRealm()
        }
        realm.close()
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

    fun savePhotoCardResponseToRealm(photoCardRes: PhotoCardRes) {
        val realm = Realm.getDefaultInstance()

        val photoCardRealm = PhotoCardRealm(photoCardRes)

        realm.executeTransaction { it.insertOrUpdate(photoCardRealm) }
        realm.close()
    }

    fun savePhotoCardFavorite(photoId: String, userId: String) {
        val photoCard = getQueryRealmInstance()
                .where(PhotoCardRealm::class.java)
                .equalTo("id", photoId)
                .findFirst()
        val favoritesAlbum = getQueryRealmInstance()
                .where(AlbumRealm::class.java)
                .equalTo("owner", userId)
                .equalTo("isFavorite", true)
                .findFirst()
        getQueryRealmInstance().executeTransaction {
            favoritesAlbum.photoCards.add(photoCard)
        }
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