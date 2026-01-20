package com.privatemessenger.app.screens.users.data

import com.privatemessenger.app.common.data.AbstractBaseCloudDataSource
import com.privatemessenger.app.common.model.ApiResponse
import com.privatemessenger.app.retrofit.api.ServerApi
import com.privatemessenger.app.screens.users.dto.UserDto
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface UsersCloudDataSource {
    fun getUsers(): Flow<ApiResponse<List<UserDto>>>
}

class UsersCloudDataSourceImpl @Inject constructor(
    private val serverApi: ServerApi
) : AbstractBaseCloudDataSource(), UsersCloudDataSource {
    override fun getUsers(): Flow<ApiResponse<List<UserDto>>> = safeApiCall {
        serverApi.getUsers()
    }
}