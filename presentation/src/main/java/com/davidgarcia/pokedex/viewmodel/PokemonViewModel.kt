package com.davidgarcia.pokedex.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidgarcia.pokedex.di.DispatcherProvider
import com.davidgarcia.pokedex.model.Pokemon
import com.davidgarcia.pokedex.mvi.PokemonIntent
import com.davidgarcia.pokedex.mvi.PokemonState
import com.davidgarcia.pokedex.usecase.GetPokemonListUseCase
import com.davidgarcia.pokedex.usecase.SearchPokemonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

@HiltViewModel
class PokemonViewModel @Inject constructor(
    private val getPokemonListUseCase: GetPokemonListUseCase,
    private val searchPokemonUseCase: SearchPokemonUseCase,
    private val dispatchers: DispatcherProvider
): ViewModel() {
    private val _state = MutableStateFlow<PokemonState>(PokemonState.Loading)
    val state: StateFlow<PokemonState> = _state.asStateFlow()

    private val intentChannel = Channel<PokemonIntent>(capacity = Channel.BUFFERED)
    private val bufferMutex = Mutex()
    private val buffer = mutableListOf<Pokemon>()

    init {
        handleIntents()
        intentChannel.trySend(PokemonIntent.LoadPokemonList)
    }

    /**
     * Send an intent safely
     * */
    fun sendIntent(intent: PokemonIntent) {
        intentChannel.trySend(intent)
    }

    /**
     * Process intents on main dispatcher
     * */
    private fun handleIntents() = viewModelScope.launch(dispatchers.main) {
        intentChannel
            .consumeAsFlow()
            .collect { intent ->
                when (intent) {
                    is PokemonIntent.LoadPokemonList -> loadPokemonList()
                    is PokemonIntent.Search -> search(intent.query)
                }
            }
    }

    /**
     * Load each Pokemon and update state as they come
     */
    private fun loadPokemonList() = viewModelScope.launch(dispatchers.main) {
        getPokemonListUseCase.invoke()
            .onStart {
                _state.value = PokemonState.Loading
                bufferMutex.withLock { buffer.clear() }
            }
            .catch { e ->
                _state.value = PokemonState.Error(e)
            }
            .collect { pokemon ->
                bufferMutex.withLock {
                    buffer.add(pokemon)
                    _state.value = PokemonState.Success(buffer.toList())
                }
            }
    }

    private fun search(query: String) = viewModelScope.launch(dispatchers.main) {
        searchPokemonUseCase.invoke(query)
            .onStart {
                _state.value = PokemonState.Loading
            }
            .catch { e -> _state.value = PokemonState.Error(e) }
            .collect { list -> _state.value = PokemonState.Success(list) }
    }
}
