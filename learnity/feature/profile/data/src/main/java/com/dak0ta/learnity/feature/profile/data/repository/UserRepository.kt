package com.dak0ta.learnity.feature.profile.data.repository

import com.dak0ta.learnity.core.domain.User
import kotlinx.coroutines.flow.Flow

internal interface UserRepository {

    suspend fun getUserMe(forceUpdate: Boolean = false): User
    fun observeUserMeCache(id: Int): Flow<User?>
    suspend fun updateUser(user: User)
}
