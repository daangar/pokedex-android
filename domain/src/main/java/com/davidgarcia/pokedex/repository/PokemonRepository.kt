package com.davidgarcia.pokedex.repository

import com.davidgarcia.pokedex.model.Pokemon
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {
    /**
     * Returns a flow that emits each Pokemon one by one as they are ready.
     * */
    fun getPokemonList(): Flow<Pokemon>

    /**
     * Returns a flow that emits matching Pokemon when searching by id or name.
     * */
    fun searchPokemon(query: String): Flow<List<Pokemon>>
}
