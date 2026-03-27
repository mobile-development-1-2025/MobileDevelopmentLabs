package com.dak0ta.learnity.core.datastore.domain.usecase.theme

import com.dak0ta.learnity.core.domain.AppTheme

interface UpdateAppThemeUseCase {

    suspend operator fun invoke(appTheme: AppTheme)
}
