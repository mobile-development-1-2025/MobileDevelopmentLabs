package com.dak0ta.learnity.core.datastore.data.usecase.theme

import com.dak0ta.learnity.core.datastore.data.repository.UserPreferencesRepository
import com.dak0ta.learnity.core.datastore.domain.usecase.theme.GetAppThemeUseCase
import com.dak0ta.learnity.core.domain.AppTheme
import kotlinx.coroutines.flow.first
import javax.inject.Inject

internal class GetAppThemeUseCaseImpl @Inject constructor(
    private val repository: UserPreferencesRepository,
) : GetAppThemeUseCase {

    override suspend fun invoke(): AppTheme = repository.appThemeFlow.first()
}
