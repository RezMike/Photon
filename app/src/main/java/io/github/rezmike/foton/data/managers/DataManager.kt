package io.github.rezmike.foton.data.managers

import io.github.rezmike.foton.App
import io.github.rezmike.foton.data.network.RestService
import io.github.rezmike.foton.data.network.req.LoginReq
import io.github.rezmike.foton.data.network.transformers.*
import io.github.rezmike.foton.data.storage.realm.AlbumRealm
import io.github.rezmike.foton.data.storage.realm.PhotoCardRealm
import io.github.rezmike.foton.data.storage.realm.UserRealm
import io.github.rezmike.foton.di.components.DaggerDataManagerComponent
import io.github.rezmike.foton.di.modules.LocalModule
import io.github.rezmike.foton.di.modules.NetworkModule
import io.github.rezmike.foton.utils.writePhotoToDisk
import rx.Completable
import rx.Observable
import rx.Single
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
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.io())
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
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.io())
                .doOnNext { if (!it.active) realmManager.deleteFromRealm(PhotoCardRealm::class.java, it.id) }
                .filter { it.active }
                .doOnNext { realmManager.savePhotoCardResponseToRealm(it) }
                .toCompletable()
    }

    fun getPhotoFileComplFromNetwork(photoUrl: String): Completable {
        return restService.getPhotoFile(photoUrl)
                .compose(PhotoFileCallTransformer())
                .flatMap { writePhotoToDisk(it) }
                .subscribeOn(Schedulers.newThread())
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
                .subscribeOn(Schedulers.newThread())
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
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.io())
                .doOnNext { if (!it.active) realmManager.deleteFromRealm(AlbumRealm::class.java, it.id) }
                .filter { it.active }
                .doOnNext { realmManager.saveAlbumResponseToRealm(it) }
                .toCompletable()
    }

    //endregion

    //region ======================== User ========================

    fun getUserSinFromRealm(userId: String) = realmManager.getUser(userId)

    fun getUserSinFromNetwork(userId: String): Single<UserRealm> {
        return restService.getUserInfo(preferencesManager.getLastUserUpdate(userId), userId)
                .compose(UserCallTranformer(userId))
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.io())
                .map { realmManager.saveUserResponseToRealm(it) }
                .toSingle()
    }

    //endregion
}