package com.dak0ta.learnity.feature.home.presentation

import com.dak0ta.learnity.core.domain.Quote

internal sealed interface HomeState {

    object Loading : HomeState

    data class Content(
        val quotes: List<Quote>,
        val isRefreshing: Boolean = false,
    ) : HomeState

    object Error : HomeState
}
