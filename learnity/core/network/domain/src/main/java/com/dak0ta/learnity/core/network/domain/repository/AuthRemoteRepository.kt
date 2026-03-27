package com.dak0ta.learnity.core.network.domain.repository

import com.dak0ta.learnity.core.domain.User
import com.dak0ta.learnity.core.network.domain.model.ApiResult

interface AuthRemoteRepository {

    suspend fun login(username: String, password: String): ApiResult<User>
}
