package com.dak0ta.learnity.core.network.domain.repository

import com.dak0ta.learnity.core.domain.User
import com.dak0ta.learnity.core.network.domain.model.ApiResult

interface UserRemoteRepository {

    suspend fun getUserById(id: Int): ApiResult<User>
}
