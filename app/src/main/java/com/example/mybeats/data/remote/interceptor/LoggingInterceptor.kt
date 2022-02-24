package com.example.mybeats.data.remote.interceptor

import android.content.Context
import com.elvishew.xlog.LogLevel
import com.elvishew.xlog.XLog
import com.example.mybeats.BuildConfig
import com.example.mybeats.R
import com.example.mybeats.data.remote.responses.CodeNetworkErrors
import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.internal.http2.ConnectionShutdownException
import okio.Buffer
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.Locale

@Suppress("TooManyGenericException")
class LoggingInterceptor(private val context: Context) : Interceptor {

    init {
        XLog.init(if (BuildConfig.DEBUG) LogLevel.ALL else LogLevel.NONE)
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        try {
            val time = System.currentTimeMillis()
            var requestLog = String.format(
                context.getString(R.string.retrofit_logging_request), request.url(),
                request.headers(),
                request.method() + context.getString(R.string.retrofit_request_method_type)
            )
            if (
                request.method().compareTo("post", ignoreCase = true) == 0 ||
                request.method().compareTo("get", ignoreCase = true) != 0
            ) {
                requestLog = requestLog + "\n" + bodyToString(request)
            }
            XLog.d("\nRequest Log:\n$requestLog")

            val response = chain.proceed(request)
            val time2 = System.currentTimeMillis()
            val responseLog = String.format(
                Locale.getDefault(),
                context.getString(R.string.retrofit_logging_response),
                response.request().url(),
                response.code(),
                (time2 - time).toDouble(),
                response.headers()
            )

            val bodyString = response.body()!!.string()

            if (BuildConfig.DEBUG) {
                XLog.d("\nResponse Log:\n${request.method()} Method\n$responseLog")
                XLog.json(bodyString)
            }

            return response.newBuilder()
                .body(ResponseBody.create(response.body()?.contentType(), bodyString))
                .build()
        } catch (e: Exception) {
            XLog.e("Request Exception: ${e.message}", e)
            val code = mappedCodeException(e)
            return Response.Builder()
                .request(request)
                .protocol(Protocol.HTTP_1_1)
                .code(code)
                .message("${e.message}")
                .body(ResponseBody.create(null, "$e")).build()
        }
    }

    private fun mappedCodeException(e: Exception): Int {
        return when (e) {
            is SocketTimeoutException -> CodeNetworkErrors.SOCKET_TIMEOUT.code
            is UnknownHostException -> CodeNetworkErrors.UNKNOWN_HOST.code
            is ConnectionShutdownException -> CodeNetworkErrors.CONNECTION_SHUTDOWN.code
            is IOException -> CodeNetworkErrors.IO.code
            else -> CodeNetworkErrors.UNMAPPED_ERROR.code
        }
    }

    private fun bodyToString(request: Request): String {
        return try {
            val copy = request.newBuilder().build()
            val buffer = Buffer()
            copy.body()!!.writeTo(buffer)
            buffer.readUtf8()
        } catch (e: IOException) {
            "${e.message}"
        }
    }
}
