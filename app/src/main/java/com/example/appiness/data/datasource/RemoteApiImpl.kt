package com.example.appiness.data.datasource

import com.example.appiness.data.repository.BakersRepository
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import io.reactivex.Single
import retrofit2.Response

class RemoteApiImpl(private val remoteApi: RemoteApi) : BakersRepository {

    override fun bakersData(): Single<Response<JsonArray>> {
        return remoteApi.getBakersDetail()
    }
}