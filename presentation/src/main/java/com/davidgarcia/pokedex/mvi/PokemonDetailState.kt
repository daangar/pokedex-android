package com.davidgarcia.pokedex.mvi

import com.davidgarcia.pokedex.model.Pokemon

sealed class PokemonDetailState {
    object Loading : PokemonDetailState()
    data class Success(val detail: Pokemon) : PokemonDetailState()
    data class Error(val throwable: Throwable) : PokemonDetailState()
}
