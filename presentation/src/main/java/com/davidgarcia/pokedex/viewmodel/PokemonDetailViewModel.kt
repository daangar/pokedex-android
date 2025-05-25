package com.davidgarcia.pokedex.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidgarcia.pokedex.di.DispatcherProvider
import com.davidgarcia.pokedex.mvi.PokemonDetailIntent
import com.davidgarcia.pokedex.mvi.PokemonDetailState
import com.davidgarcia.pokedex.usecase.GetPokemonDetailUseCase
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
class PokemonDetailViewModel @Inject constructor(
    private val getDetail: GetPokemonDetailUseCase,
    private val dispatchers: DispatcherProvider
) : ViewModel() {

    private val _state = MutableStateFlow<PokemonDetailState>(PokemonDetailState.Loading)
    val state: StateFlow<PokemonDetailState> = _state.asStateFlow()

    private val intents = Channel<PokemonDetailIntent>(Channel.BUFFERED)
    private val mutex = Mutex()

    init {
        processIntents()
    }

    fun sendIntent(intent: PokemonDetailIntent) = intents.trySend(intent)

    private fun processIntents() = viewModelScope.launch(dispatchers.main) {
        intents.consumeAsFlow().collect { intent ->
            when (intent) {
                is PokemonDetailIntent.LoadDetail -> loadDetail(intent.id)
            }
        }
    }

    private fun loadDetail(id: Int) = viewModelScope.launch(dispatchers.main) {
        getDetail.invoke(id)
            .onStart { mutex.withLock { _state.value = PokemonDetailState.Loading } }
            .catch { e -> mutex.withLock { _state.value = PokemonDetailState.Error(e) } }
            .collect { detail -> mutex.withLock { _state.value = PokemonDetailState.Success(detail) } }
    }
}
