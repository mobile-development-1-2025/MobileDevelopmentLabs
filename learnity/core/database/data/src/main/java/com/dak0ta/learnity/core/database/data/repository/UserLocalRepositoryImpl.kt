package com.dak0ta.learnity.core.database.data.repository

import com.dak0ta.learnity.core.database.data.dao.UserDao
import com.dak0ta.learnity.core.database.data.mapper.toDomain
import com.dak0ta.learnity.core.database.data.mapper.toEntity
import com.dak0ta.learnity.core.database.domain.repository.UserLocalRepository
import com.dak0ta.learnity.core.domain.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Singleton

@Singleton
internal class UserLocalRepositoryImpl(
    private val userDao: UserDao,
) : UserLocalRepository {

    override fun observeUser(id: Int): Flow<User?> {
        return userDao.getByIdFlow(id).map { it?.toDomain() }
    }

    override suspend fun getUser(id: Int): User? {
        return userDao.getByIdOnce(id)?.toDomain()
    }

    override suspend fun upsertUser(user: User) {
        userDao.insert(user.toEntity())
    }

    override suspend fun deleteUser(id: Int) {
        userDao.deleteById(id)
    }
}
