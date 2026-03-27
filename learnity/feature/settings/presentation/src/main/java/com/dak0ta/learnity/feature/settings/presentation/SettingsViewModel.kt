package com.dak0ta.learnity.feature.settings.presentation

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.dak0ta.learnity.core.coroutine.runSuspendCatching
import com.dak0ta.learnity.core.datastore.domain.usecase.theme.GetAppThemeUseCase
import com.dak0ta.learnity.core.datastore.domain.usecase.theme.ObserveAppThemeUseCase
import com.dak0ta.learnity.core.datastore.domain.usecase.theme.UpdateAppThemeUseCase
import com.dak0ta.learnity.core.domain.AppTheme
import com.dak0ta.learnity.core.mvvm.BaseViewModel
import com.dak0ta.learnity.feature.settings.presentation.ui.SettingsUiState
import com.dak0ta.learnity.feature.settings.presentation.ui.SettingsUiStateMapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class SettingsViewModel @Inject constructor(
    private val getAppThemeUseCase: GetAppThemeUseCase,
    private val observeAppThemeUseCase: ObserveAppThemeUseCase,
    private val updateAppThemeUseCase: UpdateAppThemeUseCase,
    uiStateMapper: SettingsUiStateMapper,
) : BaseViewModel() {

    private val dataState = MutableStateFlow<SettingsState>(SettingsState.Loading)
    val uiState: StateFlow<SettingsUiState> = dataState.map(uiStateMapper)
        .stateInViewModel(SettingsUiState.Loading)

    override fun onFirstInit() {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            runSuspendCatching {
                getAppThemeUseCase()
            }
                .onSuccess { appTheme ->
                    dataState.value = SettingsState.Content(appTheme)
                    observeAppTheme()
                }
                .onFailure {
                    Log.e(TAG, "Data loading failed", it)
                    dataState.value = SettingsState.Error
                }
        }
    }

    private fun observeAppTheme() {
        viewModelScope.launch {
            observeAppThemeUseCase().onEach { appTheme ->
                dataState.update { currentState ->
                    if (currentState !is SettingsState.Content) return@update currentState
                    currentState.copy(appTheme = appTheme)
                }
            }
                .launchIn(this)
        }
    }

    internal fun onRetryClick() {
        dataState.update { SettingsState.Loading }
        loadData()
    }

    internal fun onThemeSelect(newTheme: AppTheme) {
        viewModelScope.launch {
            updateAppThemeUseCase(newTheme)
        }
    }

    private companion object {

        const val TAG = "Learnity:SettingsViewModel"
    }
}
