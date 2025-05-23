package com.davidgarcia.pokedex.di

import kotlinx.coroutines.CoroutineDispatcher

/**
 * Provide dispatchers for easier testing.
 */
interface DispatcherProvider {
    val io: CoroutineDispatcher
    val default: CoroutineDispatcher
    val main: CoroutineDispatcher
}
