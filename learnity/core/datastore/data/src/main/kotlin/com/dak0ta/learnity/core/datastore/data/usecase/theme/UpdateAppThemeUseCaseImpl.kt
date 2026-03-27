package com.dak0ta.learnity.core.datastore.data.usecase.theme

import com.dak0ta.learnity.core.datastore.data.repository.UserPreferencesRepository
import com.dak0ta.learnity.core.datastore.domain.usecase.theme.UpdateAppThemeUseCase
import com.dak0ta.learnity.core.domain.AppTheme
import javax.inject.Inject

internal class UpdateAppThemeUseCaseImpl @Inject constructor(
    private val repository: UserPreferencesRepository,
) : UpdateAppThemeUseCase {

    override suspend fun invoke(appTheme: AppTheme) {
        repository.updateAppTheme(appTheme)
    }
}
