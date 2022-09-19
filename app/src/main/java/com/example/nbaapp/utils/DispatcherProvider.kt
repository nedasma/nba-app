package com.example.nbaapp.utils

import kotlinx.coroutines.CoroutineDispatcher

/**
 * An interface for coroutine dispatchers to provide as a singleton in the project which can
 * be injected in other places.
 */
interface DispatcherProvider {
    val main: CoroutineDispatcher
    val io: CoroutineDispatcher
    val default: CoroutineDispatcher
    val unconfined: CoroutineDispatcher
}