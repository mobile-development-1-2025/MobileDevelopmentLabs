package com.dak0ta.learnity.core.network.data.api.service

import com.dak0ta.learnity.core.network.data.api.dto.UserDto
import retrofit2.http.GET
import retrofit2.http.Path

internal interface UserService {

    @GET("users/{id}")
    suspend fun getUserById(@Path("id") id: Int): UserDto
}
