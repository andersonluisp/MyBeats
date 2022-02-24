package com.example.mybeats.data.remote.responses

import com.example.mybeats.R

sealed class ResultRemote<out T> {
    data class Success<out T>(val response: T) : ResultRemote<T>()
    sealed class ErrorResponse : ResultRemote<Nothing>() {
        data class MappedError(val error: NetworkErrors) : ErrorResponse()
        data class UnknownError(val messageError: String) : ErrorResponse()
    }

    enum class NetworkErrors(val code: Int, val stringId: Int) {
        SOCKET_TIMEOUT(CodeNetworkErrors.SOCKET_TIMEOUT.code, R.string.socket_timeout_message),
        UNKNOWN_HOST(CodeNetworkErrors.UNKNOWN_HOST.code, R.string.unknown_host_message),
        CONNECTION_SHUTDOWN(CodeNetworkErrors.CONNECTION_SHUTDOWN.code, R.string.connection_shutdown_message),
        IO(CodeNetworkErrors.IO.code, R.string.io_message)
    }
}
