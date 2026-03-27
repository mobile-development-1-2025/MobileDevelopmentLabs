package com.dak0ta.learnity.core.coroutine

import kotlinx.coroutines.CoroutineDispatcher

interface CoroutineDispatchers {

    val main: CoroutineDispatcher
    val io: CoroutineDispatcher
    val default: CoroutineDispatcher
    val immediate: CoroutineDispatcher
}
