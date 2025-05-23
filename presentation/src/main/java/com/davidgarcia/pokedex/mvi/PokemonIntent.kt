package com.davidgarcia.pokedex.mvi

sealed class PokemonIntent {
    data object LoadPokemonList: PokemonIntent()
    data class Search(val query: String): PokemonIntent()
}
