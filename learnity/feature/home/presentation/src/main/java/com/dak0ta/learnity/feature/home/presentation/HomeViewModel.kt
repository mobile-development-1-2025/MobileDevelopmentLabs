package com.dak0ta.learnity.feature.home.presentation

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.dak0ta.learnity.core.coroutine.runSuspendCatching
import com.dak0ta.learnity.core.mvvm.BaseViewModel
import com.dak0ta.learnity.feature.home.domain.usecase.GetQuotesUseCase
import com.dak0ta.learnity.feature.home.domain.usecase.ObserveQuotesUseCase
import com.dak0ta.learnity.feature.home.domain.usecase.RefreshQuotesUseCase
import com.dak0ta.learnity.feature.home.domain.usecase.UpdateLikeQuoteUseCase
import com.dak0ta.learnity.feature.home.presentation.ui.HomeUiState
import com.dak0ta.learnity.feature.home.presentation.ui.HomeUiStateMapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class HomeViewModel @Inject constructor(
    private val getQuotesUseCase: GetQuotesUseCase,
    private val updateLikeQuoteUseCase: UpdateLikeQuoteUseCase,
    private val refreshQuotesUseCase: RefreshQuotesUseCase,
    private val observeQuotesUseCase: ObserveQuotesUseCase,
    uiStateMapper: HomeUiStateMapper,
) : BaseViewModel() {

    private val dataState = MutableStateFlow<HomeState>(HomeState.Loading)
    val uiState: StateFlow<HomeUiState> = dataState.map(uiStateMapper)
        .stateInViewModel(HomeUiState.Loading)

    override fun onFirstInit() {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            runSuspendCatching {
                getQuotesUseCase()
            }
                .onSuccess { quotes ->
                    dataState.value = HomeState.Content(quotes)
                    observeUsers()
                }
                .onFailure {
                    Log.e(TAG, "Data loading failed", it)
                    dataState.value = HomeState.Error
                }
        }
    }

    private fun observeUsers() {
        viewModelScope.launch {
            observeQuotesUseCase().onEach { users ->
                if (users.isNotEmpty()) {
                    dataState.update { currentState ->
                        if (currentState !is HomeState.Content) return@update currentState
                        currentState.copy(quotes = users)
                    }
                }
            }
                .launchIn(this)
        }
    }

    internal fun onLikeClick(quoteId: Int, isLiked: Boolean) {
        viewModelScope.launch {
            runSuspendCatching {
                updateLikeQuoteUseCase(quoteId, isLiked)
            }
                .onFailure {
                    Log.e(TAG, "updateLikeQuoteUseCase has failed", it)
                }
        }
    }

    internal fun onRefresh() {
        viewModelScope.launch {
            updateContentState {
                it.copy(isRefreshing = true)
            }
            runSuspendCatching {
                refreshQuotesUseCase()
            }
                .onFailure {
                    Log.e(TAG, "refreshQuotesUseCase has failed", it)
                }
            updateContentState { currentState ->
                currentState.copy(isRefreshing = false)
            }
        }
    }

    internal fun onRetryClick() {
        dataState.update { HomeState.Loading }
        loadData()
    }

    private fun updateContentState(block: (HomeState.Content) -> HomeState.Content) {
        dataState.update { oldState ->
            if (oldState is HomeState.Content) {
                block(oldState)
            } else {
                oldState
            }
        }
    }

    private companion object {

        const val TAG = "Learnity:HomeViewModel"
    }
}
