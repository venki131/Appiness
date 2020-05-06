package com.example.appiness.core.application

import com.example.appiness.core.others.Status

class ResponseApi {
     val status: Status
     var flagValue = 0
     val data: Any?
     val error: Throwable?

    constructor(status: Status, data: Any?, error: Throwable?, handleUi: Int) {
        this.status = status
        this.data = data
        this.error = error
        flagValue = handleUi
    }

    constructor(status: Status, data: Any?, error: Throwable?) {
        this.status = status
        this.data = data
        this.error = error
    }

    companion object {
        fun loading(): ResponseApi {
            return ResponseApi(Status.Loading, null, null)
        }

        fun success(data: Any): ResponseApi {
            return ResponseApi(Status.Success, data, null)
        }

        fun fail(data: Any): ResponseApi {
            return ResponseApi(Status.Fail, data, null)
        }

        fun authFail(data: Any): ResponseApi {
            return ResponseApi(Status.AuthFail, null, null)
        }

        fun error(error: Throwable): ResponseApi {
            return ResponseApi(Status.Error, null, null)
        }

        fun defaultError(error: String?): ResponseApi {
            return ResponseApi(Status.DefaultError, error, null)
        }

        fun genericUIHandlingCallBack(
            status: Status,
            data: Any?,
            handleUi: Int
        ): ResponseApi {
            return ResponseApi(status, data, null, handleUi)
        }

        fun genericCallBack(status: Status, data: Any?): ResponseApi {
            return ResponseApi(status, data, null)
        }
    }
}