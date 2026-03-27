package com.dak0ta.learnity.core.network.domain.model

sealed interface ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>
    data class Failure(val error: NetworkError) : ApiResult<Nothing>
}
