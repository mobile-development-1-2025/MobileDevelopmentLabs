package com.privatemessenger.app.screens.auth.data

import android.content.Context
import android.util.Base64
import com.google.gson.Gson
import com.privatemessenger.app.R
import com.privatemessenger.app.common.model.ApiResponse
import com.privatemessenger.app.common.model.UiState
import com.privatemessenger.app.screens.auth.model.AccessTokenModel
import com.privatemessenger.app.screens.auth.crypto.RSAUtils
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last
import javax.inject.Inject


interface AuthRepository {
    fun signIn(username: String, password: String): Flow<UiState<Unit>>
    fun refreshToken(): Flow<UiState<Unit>>
    fun isAuthenticated(): Boolean
    fun getAccessTokenModel(): AccessTokenModel
    fun getPublicKey(): String
    fun getPrivateKey(): String
}

class AuthRepositoryImpl @Inject constructor(
    private val authLocalDataSource: AuthLocalDataSource,
    private val authCloudDataSource: AuthCloudDataSource,
    @ApplicationContext private val context: Context
) : AuthRepository {
    override fun signIn(username: String, password: String): Flow<UiState<Unit>> = flow {
        val logInRes = authCloudDataSource.signIn(username, password).last()
        if (logInRes is ApiResponse.Error) {
            if (logInRes.code == 401) {
                emit(UiState.Error(context.getString(R.string.invalid_credentials)))
            } else {
                emit(UiState.Error(logInRes.toString(context)))
            }
            return@flow
        }

        val data = (logInRes as ApiResponse.Success).data
        authLocalDataSource.setAccessToken(data.accessToken)
        authLocalDataSource.setRefreshToken(data.refreshToken)

        val keyPair = RSAUtils.generateKeyPair()
        authLocalDataSource.setPublicKey(keyPair.first)
        authLocalDataSource.setPrivateKey(keyPair.second)

        val setPublicKeyRes = authCloudDataSource.setPublicKey(keyPair.first).last()
        if (setPublicKeyRes is ApiResponse.Error) {
            emit(UiState.Error(setPublicKeyRes.toString(context)))
            return@flow
        }

        emit(UiState.Success(Unit))
        return@flow
    }

    override fun refreshToken(): Flow<UiState<Unit>> = flow {
        val token = authLocalDataSource.getRefreshToken()
        if (token != null) {
            val refreshRes = authCloudDataSource.refreshToken(token).last()
            if (refreshRes is ApiResponse.Success) {
                val data = refreshRes.data
                authLocalDataSource.setAccessToken(data.accessToken)
                authLocalDataSource.setRefreshToken(data.refreshToken)
                emit(UiState.Success(Unit))
            } else {
                emit(UiState.Error((refreshRes as ApiResponse.Error).toString(context)))
            }
        } else {
            emit(UiState.Success(Unit))
        }
    }

    override fun isAuthenticated(): Boolean = authLocalDataSource.getAccessToken() != null

    override fun getAccessTokenModel(): AccessTokenModel {
        val token =
            authLocalDataSource.getAccessToken() ?: throw IllegalStateException("JWT token is null")
        val split = token.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }
            .toTypedArray()
        val bodyJson = getJson(split[1])

        return Gson().fromJson(bodyJson, AccessTokenModel::class.java)
    }

    override fun getPublicKey(): String = authLocalDataSource.getPublicKey()
    override fun getPrivateKey(): String  = authLocalDataSource.getPrivateKey()

    private fun getJson(strEncoded: String): String {
        val decodedBytes: ByteArray = Base64.decode(strEncoded, Base64.URL_SAFE)
        return String(decodedBytes, charset("UTF-8"))
    }
}