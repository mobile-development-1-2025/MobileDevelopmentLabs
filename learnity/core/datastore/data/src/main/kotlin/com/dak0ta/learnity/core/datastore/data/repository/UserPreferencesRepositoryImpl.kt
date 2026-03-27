package com.dak0ta.learnity.core.datastore.data.repository

import androidx.datastore.core.DataStore
import com.dak0ta.learnity.core.datastore.data.mapper.domainToProtTheme
import com.dak0ta.learnity.core.datastore.data.mapper.protoToDomainTheme
import com.dak0ta.learnity.core.datastore.proto.UserPreferences
import com.dak0ta.learnity.core.domain.AppTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class UserPreferencesRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<UserPreferences>,
) : UserPreferencesRepository {

    override val userIdFlow: Flow<Int?> = dataStore.data
        .map { it.userId }
        .distinctUntilChanged()

    override val appThemeFlow: Flow<AppTheme> = dataStore.data
        .map { protoToDomainTheme(it.appTheme) }
        .distinctUntilChanged()

    override suspend fun updateUserId(id: Int?) {
        dataStore.updateData {
            val builder = it.toBuilder()
            if (id == null) builder.clearUserId() else builder.setUserId(id)
            builder.build()
        }
    }

    override suspend fun updateAppTheme(appTheme: AppTheme) {
        dataStore.updateData {
            it.toBuilder()
                .setAppTheme(domainToProtTheme(appTheme))
                .build()
        }
    }

    override suspend fun clearAll() {
        dataStore.updateData {
            it.toBuilder()
                .clearUserId()
                .build()
        }
    }
}
