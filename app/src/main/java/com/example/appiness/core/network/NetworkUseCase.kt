package com.example.appiness.core.network

import com.example.appiness.core.application.ResponseApi
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import io.reactivex.Single
import retrofit2.Response

abstract class NetworkUseCase {
    abstract fun isAvailInternet(): Single<Boolean>

    abstract fun response200(response: Response<JsonArray>): ResponseApi

    abstract fun response400(response: Response<JsonArray>): ResponseApi

    abstract fun response401(): ResponseApi

    abstract fun responseDefault(): ResponseApi
}