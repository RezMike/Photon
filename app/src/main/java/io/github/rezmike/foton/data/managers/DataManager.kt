package io.github.rezmike.foton.data.managers

import io.github.rezmike.foton.App
import io.github.rezmike.foton.data.network.RestService
import io.github.rezmike.foton.data.network.res.SuccessRes
import io.github.rezmike.foton.data.network.transformer.*
import io.github.rezmike.foton.data.storage.AlbumRealm
import io.github.rezmike.foton.data.storage.PhotoCardRealm
import io.github.rezmike.foton.data.storage.UserRealm
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

//    private var restCallTransformer: RestCallTransformer<Any>

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

//        restCallTransformer = RestCallTransformer()
    }

    //region ======================== Auth ========================

    fun isUserAuth(): Boolean {
        return preferencesManager.isUserAuth()
    }

    fun logoutUser() {
        preferencesManager.deleteAuthToken()
    }

    //endregion

    //region ======================== PhotoCard ========================

    fun getPhotoCardObsFromRealm(): Observable<PhotoCardRealm> {
        return realmManager.getAllPhotoCards()
    }

    fun getPhotoCardComplFromNetwork(): Completable {
        return restService.getAllPhotoCards(preferencesManager.getLastPhotoCardsUpdate())
                .compose(PhotosCallTransformer())
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

    fun savePhotoFavoriteSin(photoId: String): Single<SuccessRes> {
        return restService.savePhotoOnFavorite(preferencesManager.getAuthToken()!!, preferencesManager.getUserId()!!, photoId)
                .compose(SuccessCallTransformer())
                .subscribeOn(Schedulers.newThread())
                .toSingle()
    }

    //endregion

    //region ======================== Album ========================

    fun getAlbumComplFromNetwork(): Completable {
        return Observable.just(preferencesManager.isUserAuth())
                .filter { it }
                .flatMap { restService.getAllAlbums(preferencesManager.getLastAlbumsUpdate(), preferencesManager.getUserId()!!) }
                .compose(AlbumsRestCallTransformer())
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

    fun getUserSingleFromRealm(userId: String) = realmManager.getUser(userId)

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