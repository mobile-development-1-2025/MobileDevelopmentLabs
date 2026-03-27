package com.dak0ta.learnity.core.database.domain.repository

import com.dak0ta.learnity.core.domain.User
import kotlinx.coroutines.flow.Flow

interface UserLocalRepository {

    fun observeUser(id: Int): Flow<User?>
    suspend fun getUser(id: Int): User?
    suspend fun upsertUser(user: User)
    suspend fun deleteUser(id: Int)
}
