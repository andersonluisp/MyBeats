package com.example.mybeats.data.remote.responses

import com.example.mybeats.R

@Suppress("MagicNumber")
sealed class ResultRemote<out T> {
    data class Success<out T>(val response: T) : ResultRemote<T>()
    sealed class ErrorResponse : ResultRemote<Nothing>() {
        data class MappedError(val error: NetworkErrors) : ErrorResponse()
        data class UnknownError(val messageError: String) : ErrorResponse()
    }
    enum class NetworkErrors(val code: Int, val stringId: Int) {
        SOCKET_TIMEOUT(901, R.string.socket_timeout_message),
        UNKNOWN_HOST(902, R.string.unknown_host_message),
        CONNECTION_SHUTDOWN(903, R.string.connection_shutdown_message),
        IO(904, R.string.io_message)
    }
}
