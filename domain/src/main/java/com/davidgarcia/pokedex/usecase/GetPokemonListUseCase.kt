package com.davidgarcia.pokedex.usecase

import com.davidgarcia.pokedex.model.Pokemon
import com.davidgarcia.pokedex.repository.PokemonRepository
import dagger.Reusable
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@Reusable //Use case can be reused when possible
class GetPokemonListUseCase @Inject constructor(
    private val repo: PokemonRepository
) {
    //Call the repository to get the Pokemon flow
    operator fun invoke(): Flow<Pokemon> = repo.getPokemonList()
}
