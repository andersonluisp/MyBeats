package com.example.mybeats.data.remote.extension

import com.example.mybeats.data.remote.responses.ResultRemote
import retrofit2.HttpException

fun Throwable.mapRemoteErrors(): ResultRemote.ErrorResponse {
    return when (this) {
        is HttpException -> {
            ResultRemote.ErrorResponse.HttpError(this)
        }
        else -> ResultRemote.ErrorResponse.Unknown(this)
    }
}