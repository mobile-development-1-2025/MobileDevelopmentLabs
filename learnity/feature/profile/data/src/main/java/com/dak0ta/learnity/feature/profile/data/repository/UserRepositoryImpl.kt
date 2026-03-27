package com.dak0ta.learnity.feature.profile.data.repository

import com.dak0ta.learnity.core.coroutine.CoroutineDispatchers
import com.dak0ta.learnity.core.database.domain.cache.CacheManager
import com.dak0ta.learnity.core.database.domain.repository.UserLocalRepository
import com.dak0ta.learnity.core.datastore.domain.usecase.userid.GetUserIdUseCase
import com.dak0ta.learnity.core.domain.User
import com.dak0ta.learnity.core.network.domain.model.ApiResult
import com.dak0ta.learnity.core.network.domain.repository.UserRemoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class UserRepositoryImpl @Inject constructor(
    private val local: UserLocalRepository,
    private val remote: UserRemoteRepository,
    private val cacheManager: CacheManager,
    private val getUserIdUseCase: GetUserIdUseCase,
    private val dispatchers: CoroutineDispatchers,
) : UserRepository {

    override suspend fun getUserMe(forceUpdate: Boolean): User = withContext(dispatchers.io) {
        val userId = getUserIdUseCase() ?: error("User ID not found in datastore")

        val isCacheActual = cacheManager.isCacheActual(CACHE_KEY_USER_ME)
        if (!forceUpdate && isCacheActual) {
            local.getUser(userId) ?: fetchAndCacheUser(userId)
        } else {
            fetchAndCacheUser(userId)
        }
    }

    override fun observeUserMeCache(id: Int): Flow<User?> {
        return local.observeUser(id).distinctUntilChanged()
    }

    override suspend fun updateUser(user: User) {
        local.upsertUser(user)
    }

    private suspend fun fetchAndCacheUser(id: Int): User {
        return when (val result = remote.getUserById(id)) {
            is ApiResult.Success -> {
                val remoteUser = result.data
                val localUser = local.getUser(id)

                val merged = if (localUser != null && localUser.isLocallyEdited) {
                    remoteUser.copy(
                        firstName = localUser.firstName,
                        lastName = localUser.lastName,
                        isLocallyEdited = true,
                    )
                } else {
                    remoteUser.copy(isLocallyEdited = false)
                }

                local.upsertUser(merged)
                cacheManager.updateCacheTimestamp(CACHE_KEY_USER_ME)
                merged
            }

            is ApiResult.Failure -> {
                local.getUser(id) ?: error("Failed to load user and no cache available")
            }
        }
    }

    private companion object {

        const val CACHE_KEY_USER_ME = "cache_user_me"
    }
}
