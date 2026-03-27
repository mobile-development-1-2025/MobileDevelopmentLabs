package com.dak0ta.learnity.core.network.data.repository

import android.util.Log
import com.dak0ta.learnity.core.domain.User
import com.dak0ta.learnity.core.network.data.api.service.UserService
import com.dak0ta.learnity.core.network.data.mapper.toDomain
import com.dak0ta.learnity.core.network.data.network.SafeApiCall
import com.dak0ta.learnity.core.network.domain.model.ApiResult
import com.dak0ta.learnity.core.network.domain.repository.UserRemoteRepository

internal class UserRemoteRepositoryImpl(
    private val service: UserService,
    private val safeApiCall: SafeApiCall,
) : UserRemoteRepository {

    override suspend fun getUserById(id: Int): ApiResult<User> {
        Log.d(TAG, "Getting user by ID: $id")
        return safeApiCall("GET_USER_BY_ID") {
            service.getUserById(id).toDomain()
        }
    }

    private companion object {

        const val TAG = "Learnity:UserRemoteRepositoryImpl"
    }
}
