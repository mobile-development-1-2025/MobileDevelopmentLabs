package com.privatemessenger.app.screens.auth.data

import com.privatemessenger.app.common.data.AbstractBaseCloudDataSource
import com.privatemessenger.app.common.model.ApiResponse
import com.privatemessenger.app.retrofit.api.ServerApi
import com.privatemessenger.app.screens.auth.dto.RefreshTokensDto
import com.privatemessenger.app.screens.auth.dto.SetPublicKeyDto
import com.privatemessenger.app.screens.auth.dto.SignInDto
import com.privatemessenger.app.screens.auth.dto.TokensDto
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface AuthCloudDataSource {
    fun signIn(
        username: String,
        password: String
    ): Flow<ApiResponse<TokensDto>>

    fun refreshToken(
        refreshToken: String
    ): Flow<ApiResponse<TokensDto>>

    fun setPublicKey(
        key: String
    ): Flow<ApiResponse<Unit>>
}

class AuthCloudDataSourceImpl @Inject constructor(
    private val serverApi: ServerApi
) : AbstractBaseCloudDataSource(), AuthCloudDataSource {

    override fun signIn(username: String, password: String): Flow<ApiResponse<TokensDto>> =
        safeApiCall {
            serverApi.signIn(SignInDto(username, password))
        }

    override fun refreshToken(refreshToken: String): Flow<ApiResponse<TokensDto>> =
        safeApiCall {
            serverApi.refreshTokens(RefreshTokensDto(refreshToken))
        }

    override fun setPublicKey(key: String): Flow<ApiResponse<Unit>> = safeApiCall {
        serverApi.setPublicKey(SetPublicKeyDto(key))
    }
}