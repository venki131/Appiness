package com.example.appiness.data.datasource

import com.example.appiness.utils.UrlConstant
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET

interface RemoteApi {
    @GET(UrlConstant.BAKERS_DATA)
    fun getBakersDetail(): Single<Response<JsonArray>>
}