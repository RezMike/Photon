package io.github.rezmike.photon.data.managers

import io.github.rezmike.photon.App
import io.github.rezmike.photon.data.network.RestService
import io.github.rezmike.photon.data.network.req.AlbumReq
import io.github.rezmike.photon.data.network.req.LoginReq
import io.github.rezmike.photon.data.network.res.AvatarUrlRes
import io.github.rezmike.photon.data.network.transformers.*
import io.github.rezmike.photon.data.storage.realm.AlbumRealm
import io.github.rezmike.photon.data.storage.realm.PhotoCardRealm
import io.github.rezmike.photon.data.storage.realm.UserRealm
import io.github.rezmike.photon.di.components.DaggerDataManagerComponent
import io.github.rezmike.photon.di.modules.LocalModule
import io.github.rezmike.photon.di.modules.NetworkModule
import io.github.rezmike.photon.utils.writePhotoToDisk
import okhttp3.MultipartBody
import rx.Completable
import rx.Observable
import rx.Single
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import javax.inject.Inject

class DataManager private constructor() {

    private val TAG = "DataManager"

    @Inject
    lateinit var preferencesManager: PreferencesManager
    @Inject
    lateinit var realmManager: RealmManager
    @Inject
    lateinit var restService: RestService

    companion object {
        val INSTANCE = DataManager()
    }

    init {
        DaggerDataManagerComponent.builder()
                .appComponent(App.appComponent)
                .localModule(LocalModule())
                .networkModule(NetworkModule())
                .build().inject(this)
    }

    //region ======================== Auth ========================

    fun isUserAuth(): Boolean {
        return preferencesManager.isUserAuth()
    }

    fun loginUserCompl(loginReq: LoginReq): Completable {
        return restService.login(loginReq)
                .compose(AuthCallTransformer())
                .subscribeOn(Schedulers.io())
                .doOnNext { preferencesManager.saveUserData(it) }
                .doOnNext { realmManager.saveUserResponseToRealm(it) }
                .toCompletable()
    }

    fun logoutUser() {
        preferencesManager.deleteUserData()
    }

    //endregion

    //region ======================== PhotoCard ========================

    fun getPhotoCardObsFromRealm(): Observable<PhotoCardRealm> {
        return realmManager.getAllPhotoCards()
    }

    fun getPhotoCardComplFromNetwork(): Completable {
        return restService.getAllPhotoCards(preferencesManager.getLastPhotoCardsUpdate())
                .compose(PhotoCardsCallTransformer())
                .flatMap { Observable.from(it) }
                .subscribeOn(Schedulers.io())
                .doOnNext { if (!it.active) realmManager.deleteFromRealm(PhotoCardRealm::class.java, it.id) }
                .filter { it.active }
                .doOnNext { realmManager.savePhotoCardResponseToRealm(it) }
                .toCompletable()
    }

    fun getPhotoFileComplFromNetwork(photoUrl: String): Completable {
        return restService.getPhotoFile(photoUrl)
                .compose(PhotoFileCallTransformer())
                .flatMap { writePhotoToDisk(it) }
                .subscribeOn(Schedulers.io())
                .toCompletable()
    }


    fun savePhotoCardFavoriteComplToRealm(photoId: String): Completable {
        try {
            realmManager.savePhotoCardFavorite(photoId, preferencesManager.getUserId()!!)
            return Completable.complete()
        } catch (e: Exception) {
            return Completable.error(e)
        }
    }

    fun savePhotoCardFavoriteComplToNetwork(photoId: String): Completable {
        return restService.savePhotoOnFavorite(preferencesManager.getAuthToken()!!, preferencesManager.getUserId()!!, photoId)
                .compose(SuccessCallTransformer())
                .subscribeOn(Schedulers.io())
                .toCompletable()
    }

    //endregion

    //region ======================== Album ========================

    fun getAlbumComplFromNetwork(): Completable {
        return Observable.just(preferencesManager.isUserAuth())
                .filter { it }
                .flatMap { restService.getAllAlbums(preferencesManager.getLastAlbumsUpdate(), preferencesManager.getUserId()!!) }
                .compose(AlbumsCallTransformer())
                .flatMap { Observable.from(it) }
                .subscribeOn(Schedulers.io())
                .doOnNext { if (!it.active) realmManager.deleteFromRealm(AlbumRealm::class.java, it.id) }
                .filter { it.active }
                .doOnNext { realmManager.saveAlbumResponseToRealm(it) }
                .toCompletable()
    }

    fun createAlbum(albumReq: AlbumReq): Completable {
        return restService.createAlbum(preferencesManager.getAuthToken()!!, getUserId()!!, albumReq)
                .compose(AlbumCallTransformer())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { realmManager.saveNewAlbumResponseToRealm(it, getUserId()!!) }
                .toCompletable()
    }

    //endregion

    //region ======================== User ========================

    fun getUserId() = preferencesManager.getUserId()

    fun getUserSinFromRealm(userId: String) = realmManager.getUser(userId)

    fun getUserSinFromNetwork(userId: String): Single<UserRealm> {
        return restService.getUserInfo(preferencesManager.getLastUserUpdate(userId), userId)
                .compose(UserCallTranformer(userId))
                .subscribeOn(Schedulers.io())
                .map { realmManager.saveUserResponseToRealm(it) }
                .toSingle()
    }

    fun uploadAvatarUser(file: MultipartBody.Part): Single<AvatarUrlRes> {
        return restService.uploadAvatarUser(preferencesManager.getAuthToken()!!, getUserId()!!, file)
    }

    //endregion
}