package com.example.appiness.domain.remoteusecase

import com.example.appiness.core.application.ActivityUtil
import com.example.appiness.core.application.NetworkUtil
import com.example.appiness.core.application.ResponseApi
import com.example.appiness.core.network.NetworkUseCase
import com.example.appiness.data.model.BakersResponseModel
import com.example.appiness.data.repository.BakersRepository
import com.example.appiness.utils.AppConstant
import com.example.appiness.utils.AppConstant.Companion.RES_200
import com.example.appiness.utils.AppConstant.Companion.RES_400
import com.example.appiness.utils.AppConstant.Companion.RES_401
import com.example.appiness.utils.AppConstant.Companion.RES_DEFAULT
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.reflect.TypeToken
import io.reactivex.Single
import retrofit2.Response
import kotlin.properties.Delegates


class BakersRemoteDataUseCase(private val bakersRepository: BakersRepository) : NetworkUseCase() {
    private var apiType by Delegates.notNull<Int>()
    var gson = Gson()

    fun getBakersData(): Single<ResponseApi> {
        apiType = AppConstant.BAKERS_API
        return this.bakersRepository.bakersData()
            .map { return@map handleResponse(it) }
    }

    private fun handleResponse(response: Response<JsonArray>): ResponseApi {
        return when (response.code()) {
            RES_200 -> response200(response)
            RES_400 -> response400(response)
            RES_401 -> response401()
            RES_DEFAULT -> responseDefault()
            else -> responseDefault()
        }
    }

    override fun isAvailInternet(): Single<Boolean> {
        return NetworkUtil().hasInternetConnection()
    }

    override fun response200(response: Response<JsonArray>): ResponseApi {
        when (apiType) {
            AppConstant.BAKERS_API ->
                return ResponseApi.success(
                    gson.fromJson<List<BakersResponseModel>>(
                        response.body(),
                        object : TypeToken<List<BakersResponseModel>>() {}.type
                    )
                )
            else -> return responseDefault()
        }
    }

    override fun response400(response: Response<JsonArray>): ResponseApi {
        return try {
            val errorJson = response.errorBody()?.string()
            val errorMessage: String = ActivityUtil.errorMessageHandler(
                "Unable to connect. Please try after sometime.",
                errorJson
            )
            ResponseApi.fail(errorMessage)
        } catch (e: Exception) {
            ResponseApi.defaultError("Unable to connect. Please try after sometime.")
        }
    }

    override fun response401(): ResponseApi {
        return ResponseApi.authFail(RES_401)
    }

    override fun responseDefault(): ResponseApi {
        return ResponseApi.defaultError("Unable to connect. Please try after sometime.")
    }
}