package com.dak0ta.learnity.core.datastore.domain.usecase.theme

import com.dak0ta.learnity.core.domain.AppTheme
import kotlinx.coroutines.flow.Flow

interface ObserveAppThemeUseCase {

    operator fun invoke(): Flow<AppTheme>
}
