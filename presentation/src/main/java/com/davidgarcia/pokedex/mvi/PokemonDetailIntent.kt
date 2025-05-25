package com.davidgarcia.pokedex.mvi

sealed class PokemonDetailIntent {
    data class LoadDetail(val id: Int) : PokemonDetailIntent()
}
