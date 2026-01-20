package com.privatemessenger.app.screens.users.data

import android.content.Context
import com.privatemessenger.app.common.model.ApiResponse
import com.privatemessenger.app.common.model.UiState
import com.privatemessenger.app.screens.users.model.UserModel
import com.privatemessenger.app.screens.users.model.toModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface UsersRepository {
    fun getUsers(): Flow<UiState<List<UserModel>>>
}

class UsersRepositoryImpl @Inject constructor(
    private val usersCloudDataSource: UsersCloudDataSource,
    @ApplicationContext private val context: Context
) : UsersRepository {
    override fun getUsers(): Flow<UiState<List<UserModel>>> {
        return usersCloudDataSource.getUsers().map {
            when (it) {
                is ApiResponse.Success -> return@map UiState.Success(it.data.map { u -> u.toModel() })
                is ApiResponse.Error -> return@map UiState.Error(it.toString(context))
            }
        }
    }
}