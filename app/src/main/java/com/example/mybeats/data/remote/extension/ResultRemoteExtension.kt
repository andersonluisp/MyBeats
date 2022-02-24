package com.example.mybeats.data.remote.extension

import com.example.mybeats.data.remote.responses.ResultRemote
import com.example.mybeats.data.remote.responses.ResultRemote.NetworkErrors
import retrofit2.Response

fun <T> Response<T>.toErrorResponse(): ResultRemote.ErrorResponse {
    val networkError = NetworkErrors.values().firstOrNull {
        it.code == this.code()
    }
    return if (networkError != null) {
        ResultRemote.ErrorResponse.MappedError(networkError)
    } else {
        ResultRemote.ErrorResponse.UnknownError(this.message())
    }
}
