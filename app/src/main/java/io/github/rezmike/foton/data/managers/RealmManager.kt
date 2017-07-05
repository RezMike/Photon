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

        if (!photoCardRes.tags.isEmpty()) {
            Observable.from(photoCardRes.tags)
                    .map { TagRealm(it) }
                    .subscribe { photoCardRealm.tags.add(it) }
        }

        realm.executeTransaction { it.insertOrUpdate(photoCardRealm) }
        realm.close()
    }

    //endregion

    //region ======================== Albums ========================

    fun saveAlbumResponseToRealm(albumRes: AlbumRes) {
        val realm = Realm.getDefaultInstance()

        val albumRealm = AlbumRealm(albumRes)

        if (!albumRes.photocards.isEmpty()) {
            Observable.from(albumRes.photocards)
                    .map { PhotoCardRealm(it) }
                    .subscribe { albumRealm.photoCards.add(it) }
        }

        realm.executeTransaction { it.insertOrUpdate(albumRealm) }
        realm.close()
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


    //region ======================== User ========================

    fun getUser(userId: String): Single<UserRealm> {
        val realm = Realm.getDefaultInstance()

        val user = realm.where(UserRealm::class.java)
                .equalTo("id", userId).findFirst()

        if (user == null) return Single.error(Throwable())
        else return Single.just(user)


    }

    fun saveUserResponseToRealm(user: UserRes) {
//        val realm = Realm.getDefaultInstance()
//        val userRealm = UserRealm(user)
//        if (!user.albums.isEmpty()) {
//            Observable.from(user.albums)
//                    .doOnNext { Observable.from(it.photocards). }
    }

    //endregion
}