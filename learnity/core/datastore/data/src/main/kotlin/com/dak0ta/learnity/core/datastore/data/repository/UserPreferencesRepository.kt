package com.dak0ta.learnity.core.datastore.data.repository

import com.dak0ta.learnity.core.domain.AppTheme
import kotlinx.coroutines.flow.Flow

internal interface UserPreferencesRepository {

    val userIdFlow: Flow<Int?>
    val appThemeFlow: Flow<AppTheme>

    suspend fun updateUserId(id: Int?)
    suspend fun updateAppTheme(appTheme: AppTheme)
    suspend fun clearAll()
}
