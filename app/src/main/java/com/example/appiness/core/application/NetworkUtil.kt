package com.example.appiness.core.application

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.net.InetSocketAddress
import java.net.Socket
import java.net.SocketAddress

class NetworkUtil {
    fun hasInternetConnection(): Single<Boolean> {
        return Single.fromCallable<Boolean> {
            val socket = Socket()
            try {
                val timeoutMs = 1500
                val socketAddress: SocketAddress =
                    InetSocketAddress("8.8.8.8", 53)
                socket.connect(socketAddress, timeoutMs)
                return@fromCallable true
            } catch (e: Exception) {
                socket.close()
                return@fromCallable false
            } finally {
                socket.close()
            }
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }
}