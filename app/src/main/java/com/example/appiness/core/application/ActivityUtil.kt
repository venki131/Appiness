package com.example.appiness.core.application

import com.example.appiness.utils.KeyConstant
import org.json.JSONObject

class ActivityUtil {
    companion object {
        fun errorMessageHandler(
            defaultMsg: String,
            responseData: String?
        ): String {
            val errorJson: JSONObject
            return try {
                errorJson = JSONObject(responseData)
                val description =
                    errorJson.getJSONArray(KeyConstant.ERROR_DETAILS).opt(0) as JSONObject
                description.optString(KeyConstant.DESCRIPTION)
            } catch (e: Exception) {
                defaultMsg
            }
        }
    }
}