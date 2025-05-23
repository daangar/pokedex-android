package com.davidgarcia.pokedex.usecase

import com.davidgarcia.pokedex.model.Pokemon
import com.davidgarcia.pokedex.repository.PokemonRepository
import dagger.Reusable
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@Reusable
class SearchPokemonUseCase @Inject constructor(
    private val repo: PokemonRepository
) {
    operator fun invoke(query: String): Flow<List<Pokemon>> = repo.searchPokemon(query)
}
