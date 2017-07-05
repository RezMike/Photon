package io.github.rezmike.foton.data.network.transformer

import android.support.annotation.VisibleForTesting
import android.support.annotation.VisibleForTesting.NONE
import com.fernandocejas.frodo.annotation.RxLogObservable
import io.github.rezmike.foton.data.network.error.AccessError
import io.github.rezmike.foton.data.network.error.ErrorUtils
import io.github.rezmike.foton.data.network.error.NetworkAvailableError
import io.github.rezmike.foton.data.network.res.SuccessRes
import io.github.rezmike.foton.utils.ConstantManager
import io.github.rezmike.foton.utils.NetworkStatusChecker
import retrofit2.Response
import rx.Observable

abstract class RestCallTransformer<R> : Observable.Transformer<Response<R>, R> {

    private var isInTestMode: Boolean = false

    @RxLogObservable
    override fun call(responseObservable: Observable<Response<R>>): Observable<R> {
        val networkStatus: Observable<Boolean>
        if (isInTestMode) {
            networkStatus = Observable.just(true)
        } else {
            networkStatus = NetworkStatusChecker.isInternetAvailable()
        }
        return networkStatus
                .flatMap { aBoolean -> if (aBoolean) responseObservable else Observable.error<Response<R>>(NetworkAvailableError()) }
                .flatMap { rResponse ->
                    when (rResponse.code()) {
                        200 -> {
                            val lastModified = rResponse.headers().get(ConstantManager.LAST_MODIFIED_HEADER)
                            if (lastModified != null) saveLastModify(lastModified)
                            Observable.just(rResponse.body())
                        }
                        201->{
                            Observable.just(rResponse.body())
                        }
                        304 -> Observable.empty()
                        403 -> Observable.error(AccessError())
                        else -> Observable.error(ErrorUtils.parseError(rResponse))
                    }
                }
    }

    @VisibleForTesting(otherwise = NONE)
    fun setTestMode() {
        isInTestMode = true
    }

    abstract fun saveLastModify(lastModified: String)

}
