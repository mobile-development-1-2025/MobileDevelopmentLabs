package com.dak0ta.learnity.feature.home.presentation.ui

import com.dak0ta.learnity.core.domain.Quote
import com.dak0ta.learnity.feature.home.presentation.HomeState
import javax.inject.Inject

internal class HomeUiStateMapper @Inject constructor() : (HomeState) -> HomeUiState {

    override fun invoke(state: HomeState): HomeUiState {
        return when (state) {
            is HomeState.Loading -> HomeUiState.Loading
            is HomeState.Content -> mapContentState(state)
            is HomeState.Error -> mapErrorState()
        }
    }

    private fun mapContentState(state: HomeState.Content): HomeUiState.Content =
        HomeUiState.Content(
            quotes = mapUsersToUi(state.quotes),
            isRefreshing = state.isRefreshing,
        )

    private fun mapUsersToUi(quotes: List<Quote>): List<QuoteInfo> {
        return quotes.map { quote ->
            QuoteInfo(
                id = quote.id,
                quote = quote.quote,
                author = "© ${quote.author}",
                isLiked = quote.isLiked,
            )
        }
    }

    private fun mapErrorState(): HomeUiState.Error = HomeUiState.Error(
        title = "Something went wrong",
        description = "The data could not be uploaded. Try again later.",
        retryButtonText = "Try again",
    )
}
