package com.example.appiness.data.repository

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import io.reactivex.Single
import retrofit2.Response

interface BakersRepository {
    fun bakersData(): Single<Response<JsonArray>>
}