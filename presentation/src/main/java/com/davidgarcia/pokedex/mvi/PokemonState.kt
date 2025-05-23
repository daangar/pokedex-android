package com.davidgarcia.pokedex.mvi

import com.davidgarcia.pokedex.model.Pokemon

sealed class PokemonState {
    data object Loading: PokemonState() // Data is loading
    data class Success(val list: List<Pokemon>): PokemonState() // Data loaded
    data class Error(val throwable: Throwable): PokemonState() // Error occurred
}
