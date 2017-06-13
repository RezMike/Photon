package io.github.rezmike.foton.data.managers

import android.util.Log
import io.github.rezmike.foton.App
import io.github.rezmike.foton.data.network.RestCallTransformer
import io.github.rezmike.foton.data.network.RestService
import io.github.rezmike.foton.data.network.res.PhotoCardRes
import io.github.rezmike.foton.data.storage.AlbumRealm
import io.github.rezmike.foton.data.storage.PhotoCardRealm
import io.github.rezmike.foton.di.components.DaggerDataManagerComponent
import io.github.rezmike.foton.di.modules.LocalModule
import io.github.rezmike.foton.di.modules.NetworkModule
import io.github.rezmike.foton.utils.AppConfig
import io.github.rezmike.foton.utils.NetworkStatusChecker
import rx.Completable
import rx.Observable
import rx.schedulers.Schedulers
import java.util.*
import java.util.concurrent.TimeUnit
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
        Log.e(TAG, "LOCAL UPDATE start: " + Date())
        return Observable.interval(AppConfig.JOB_UPDATE_DATA_INTERVAL, TimeUnit.SECONDS)
                .flatMap<Boolean> { NetworkStatusChecker.isInternetAvailable() }
                .filter { it }
                .flatMap { getPhotoCardObsFromNetwork() }
                .toCompletable()
                .andThen(getAlbumObsFromNetwork().toCompletable())
    }

    //region ======================== PhotoCard ========================

    fun getPhotoCardObsFromRealm(): Observable<PhotoCardRealm> {
        return realmManager.getAllPhotoCards();
    }

    fun getPhotoCardObsFromNetwork(): Observable<PhotoCardRealm> {
        return restService.getAllPhotoCards(preferencesManager.getLastProductUpdate())
                .compose(restCallTransformer as RestCallTransformer<List<PhotoCardRes>>)
                .flatMap { Observable.from(it) }
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.io())
                .doOnNext { realmManager.savePhotoCardResponseToRealm(it) }
                .doOnNext { Log.d(TAG, "onNext") }
                .retryWhen { errorObservable ->
                    errorObservable
                            .zipWith(Observable.range(1, AppConfig.RETRY_REQUEST_COUNT), { _, retryCount -> retryCount })
                            .doOnNext { Log.e(TAG, "LOCAL UPDATE request retry count: $it ${Date()}") }
                            .map { (AppConfig.RETRY_REQUEST_BASE_DELAY * Math.pow(Math.E, it.toDouble())).toLong() }
                            .doOnNext { Log.e(TAG, "LOCAL UPDATE delay: $it") }
                            .flatMap { Observable.timer(it, TimeUnit.MILLISECONDS) }
                }
                .flatMap<PhotoCardRealm> { Observable.empty<PhotoCardRealm>() }
    }

    //endregion

    //region ======================== Album ========================

    fun getAlbumObsFromNetwork(): Observable<AlbumRealm> {
        // TODO: 14.06.2017 implement this
        TODO()
    }

    //endregion

}