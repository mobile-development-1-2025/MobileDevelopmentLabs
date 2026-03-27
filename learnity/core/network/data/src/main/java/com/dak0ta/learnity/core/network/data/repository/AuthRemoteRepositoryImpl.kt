package com.dak0ta.learnity.core.network.data.repository

import android.util.Log
import com.dak0ta.learnity.core.domain.User
import com.dak0ta.learnity.core.network.data.api.dto.AuthLoginRequestDto
import com.dak0ta.learnity.core.network.data.api.service.AuthService
import com.dak0ta.learnity.core.network.data.mapper.toDomain
import com.dak0ta.learnity.core.network.data.network.SafeApiCall
import com.dak0ta.learnity.core.network.domain.model.ApiResult
import com.dak0ta.learnity.core.network.domain.repository.AuthRemoteRepository

internal class AuthRemoteRepositoryImpl(
    private val service: AuthService,
    private val safeApiCall: SafeApiCall,
) : AuthRemoteRepository {

    override suspend fun login(username: String, password: String): ApiResult<User> {
        Log.d(TAG, "Login")
        return safeApiCall("LOGIN") {
            service.login(AuthLoginRequestDto(username, password)).toDomain()
        }
    }

    private companion object {

        const val TAG = "Learnity:AuthRemoteRepositoryImpl"
    }
}
