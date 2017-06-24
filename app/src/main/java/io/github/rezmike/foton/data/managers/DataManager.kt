package io.github.rezmike.foton.data.managers

import io.github.rezmike.foton.App
import io.github.rezmike.foton.data.network.RestCallTransformer
import io.github.rezmike.foton.data.network.RestService
import io.github.rezmike.foton.data.network.res.AlbumRes
import io.github.rezmike.foton.data.network.res.PhotoCardRes
import io.github.rezmike.foton.data.storage.PhotoCardRealm
import io.github.rezmike.foton.di.components.DaggerDataManagerComponent
import io.github.rezmike.foton.di.modules.LocalModule
import io.github.rezmike.foton.di.modules.NetworkModule
import io.github.rezmike.foton.utils.NetworkStatusChecker
import rx.Completable
import rx.Observable
import rx.schedulers.Schedulers
import javax.inject.Inject

class DataManager private constructor() {

    private val TAG = "DataManager"

    private var restCallTransformer: RestCallTransformer<Any>

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

        restCallTransformer = RestCallTransformer()
    }

    fun startUpdateLocalData(): Completable {
        return NetworkStatusChecker.isInternetAvailable()
                .toCompletable()
                .andThen(getPhotoCardComplFromNetwork())
                .andThen(getAlbumComplFromNetwork())
    }

    //region ======================== PhotoCard ========================

    fun getPhotoCardObsFromRealm(): Observable<PhotoCardRealm> {
        return realmManager.getAllPhotoCards();
    }

    private fun getPhotoCardComplFromNetwork(): Completable {
        return restService.getAllPhotoCards(preferencesManager.getLastPhotoCardsUpdate())
                .compose(restCallTransformer as RestCallTransformer<List<PhotoCardRes>>)
                .flatMap { Observable.from(it) }
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.io())
                .doOnNext { if (!it.active) realmManager.deleteFromRealm(PhotoCardRealm::class.java, it.id) }
                .filter { it.active }
                .doOnNext { realmManager.savePhotoCardResponseToRealm(it) }
                .toCompletable()
    }

    //endregion

    //region ======================== Album ========================

    private fun getAlbumComplFromNetwork(): Completable {
        return Observable.just(preferencesManager.isUserAuth())
                .filter { it }
                .flatMap { restService.getAllAlbums(preferencesManager.getLastAlbumsUpdate(), preferencesManager.getUserId()!!) }
                .compose(restCallTransformer as RestCallTransformer<List<AlbumRes>>)
                .flatMap { Observable.from(it) }
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.io())
                // TODO: 17.06.2017 check if active
                .doOnNext { realmManager.saveAlbumResponseToRealm(it) }
                .toCompletable()
    }

    //endregion

}