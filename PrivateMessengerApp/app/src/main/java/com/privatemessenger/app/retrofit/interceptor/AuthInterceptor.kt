package com.privatemessenger.app.retrofit.interceptor

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.privatemessenger.app.retrofit.exception.NotLoggedInException
import com.privatemessenger.app.screens.auth.data.AuthLocalDataSource
import com.privatemessenger.app.screens.auth.dto.TokensDto
import com.privatemessenger.app.BuildConfig
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okhttp3.internal.closeQuietly
import org.json.JSONException
import org.json.JSONObject
import java.net.HttpURLConnection
import javax.inject.Inject


class AuthInterceptor @Inject constructor(private val authLocalDataSource: AuthLocalDataSource) :
    Interceptor {
    companion object {
        private const val AUTH_HEADER = "Authorization"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        if (authLocalDataSource.getAccessToken() != null) {
            request = request.newBuilder().header(
                AUTH_HEADER, "Bearer ${authLocalDataSource.getAccessToken()}"
            ).build()
        }
        val response = chain.proceed(request)

        if (!response.isSuccessful) {
            if (response.code == HttpURLConnection.HTTP_UNAUTHORIZED) {
                response.closeQuietly()

                val authRequest = createAuthRequest()
                val authResponse = chain.proceed(authRequest)
                val newAuthInfo = getNewAuthInfo(authResponse)
                authResponse.closeQuietly()

                if (newAuthInfo != null) {
                    authLocalDataSource.setAccessToken(newAuthInfo.accessToken)
                    authLocalDataSource.setRefreshToken(newAuthInfo.refreshToken)
                    return chain.proceed(
                        request
                            .newBuilder()
                            .header(AUTH_HEADER, "Bearer ${authLocalDataSource.getAccessToken()}")
                            .build()
                    )
                }

                authLocalDataSource.setAccessToken(null)
                authLocalDataSource.setRefreshToken(null)
                throw NotLoggedInException()
            }
        }
        return response
    }

    private fun createAuthRequest(): Request {
        val refreshToken = authLocalDataSource.getRefreshToken() ?: ""

        val jsonObject = JSONObject()
        try {
            jsonObject.put("refresh_token", refreshToken)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        val mediaType = "application/json; charset=utf-8".toMediaType()
        val body = jsonObject.toString().toRequestBody(mediaType)

        return Request.Builder()
            .url(BuildConfig.BACKEND_URL + "api/auth/refresh")
            .post(body)
            .build()
    }

    private fun getNewAuthInfo(response: Response?): TokensDto? {
        val body = response?.body ?: return null
        if (response.isSuccessful) {
            val resultResponse = body.string()
            if (resultResponse.isNotEmpty()) {
                val authResponse = try {
                    Gson().fromJson(resultResponse, TokensDto::class.java)
                } catch (e: JsonSyntaxException) {
                    null
                }
                if (authResponse != null) {
                    return authResponse
                }
            }
        }
        return null
    }

}