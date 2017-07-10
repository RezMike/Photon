package io.github.rezmike.photon.utils

import android.content.Context
import android.net.ConnectivityManager
import io.github.rezmike.photon.App
import io.github.rezmike.photon.data.network.error.NetworkAvailableError
import rx.Observable

object NetworkStatusChecker {

    @JvmStatic
    fun isNetworkAvailable(): Boolean {
        val cm = App.context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = cm.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnectedOrConnecting
    }

    @JvmStatic
    fun isInternetAvailable(): Observable<Boolean> = if (isNetworkAvailable()) Observable.just(true) else Observable.error(NetworkAvailableError())
}
