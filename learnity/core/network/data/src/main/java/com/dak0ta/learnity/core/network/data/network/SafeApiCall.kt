package com.dak0ta.learnity.core.network.data.network

import android.util.Log
import com.dak0ta.learnity.core.coroutine.CoroutineDispatchers
import com.dak0ta.learnity.core.network.domain.model.ApiResult
import com.dak0ta.learnity.core.network.domain.model.NetworkError
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.JsonEncodingException
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject

internal class SafeApiCall @Inject constructor(
    private val dispatchers: CoroutineDispatchers,
) {

    @Suppress("TooGenericExceptionCaught")
    suspend operator fun <T> invoke(
        operationName: String = "API_CALL",
        block: suspend () -> T,
    ): ApiResult<T> = withContext(dispatchers.io) {
        try {
            Log.d(TAG, "Starting API call: $operationName")
            val result = block()
            Log.d(TAG, "API call successful: $operationName")
            ApiResult.Success(result)
        } catch (e: Exception) {
            Log.e(TAG, "API call failed: $operationName", e)
            val networkError = handleException(e)
            ApiResult.Failure(networkError)
        }
    }

    private fun handleException(e: Exception): NetworkError {
        return when (e) {
            is JsonEncodingException, is JsonDataException -> {
                Log.e(TAG, "JSON parsing error", e)
                NetworkError.Parse(e)
            }

            is SocketTimeoutException -> {
                Log.e(TAG, "Request timeout", e)
                NetworkError.Timeout
            }

            is IOException -> {
                Log.e(TAG, "Network I/O error", e)
                NetworkError.Network
            }

            is HttpException -> {
                Log.e(TAG, "HTTP error: ${e.code()}", e)
                val code = e.code()
                val body = try {
                    e.response()?.errorBody()?.string()
                } catch (_: Exception) {
                    null
                }
                NetworkError.Http(code = code, body = body, message = e.message())
            }

            else -> {
                Log.e(TAG, "Unknown error", e)
                NetworkError.Unknown(e)
            }
        }
    }

    private companion object {

        const val TAG = "Learnity:SafeApiCall"
    }
}
