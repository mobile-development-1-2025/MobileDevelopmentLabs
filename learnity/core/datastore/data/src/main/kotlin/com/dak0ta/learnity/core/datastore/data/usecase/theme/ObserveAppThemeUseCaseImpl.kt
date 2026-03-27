package com.dak0ta.learnity.core.datastore.data.usecase.theme

import com.dak0ta.learnity.core.datastore.data.repository.UserPreferencesRepository
import com.dak0ta.learnity.core.datastore.domain.usecase.theme.ObserveAppThemeUseCase
import com.dak0ta.learnity.core.domain.AppTheme
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class ObserveAppThemeUseCaseImpl @Inject constructor(
    private val repository: UserPreferencesRepository,
) : ObserveAppThemeUseCase {

    override fun invoke(): Flow<AppTheme> = repository.appThemeFlow
}
