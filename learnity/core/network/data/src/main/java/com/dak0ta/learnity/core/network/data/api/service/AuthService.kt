package com.dak0ta.learnity.core.network.data.api.service

import com.dak0ta.learnity.core.network.data.api.dto.AuthLoginRequestDto
import com.dak0ta.learnity.core.network.data.api.dto.UserDto
import retrofit2.http.Body
import retrofit2.http.POST

internal interface AuthService {

    @POST("auth/login")
    suspend fun login(@Body request: AuthLoginRequestDto): UserDto
}
