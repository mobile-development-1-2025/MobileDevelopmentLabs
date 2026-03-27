package com.dak0ta.learnity.feature.authorization.data.repository

import com.dak0ta.learnity.core.coroutine.CoroutineDispatchers
import com.dak0ta.learnity.core.database.domain.cache.CacheManager
import com.dak0ta.learnity.core.database.domain.repository.UserLocalRepository
import com.dak0ta.learnity.core.datastore.domain.usecase.userid.UpdateUserIdUseCase
import com.dak0ta.learnity.core.network.domain.model.ApiResult
import com.dak0ta.learnity.core.network.domain.repository.AuthRemoteRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class AuthorizationRepositoryImpl @Inject constructor(
    private val local: UserLocalRepository,
    private val remote: AuthRemoteRepository,
    private val cacheManager: CacheManager,
    private val updateUserIdUseCase: UpdateUserIdUseCase,
    private val dispatchers: CoroutineDispatchers,
) : AuthorizationRepository {

    override suspend fun login(username: String, password: String): Unit = withContext(dispatchers.io) {
        when (val result = remote.login(username, password)) {
            is ApiResult.Success -> {
                val remoteUser = result.data
                val localUser = local.getUser(remoteUser.id)

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
                updateUserIdUseCase(merged.id)
            }

            is ApiResult.Failure -> {
                error("Failed to load user and no cache available")
            }
        }
    }

    private companion object {

        const val CACHE_KEY_USER_ME = "cache_user_me"
    }
}
