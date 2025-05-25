package com.davidgarcia.pokedex.usecase

import com.davidgarcia.pokedex.model.Pokemon
import com.davidgarcia.pokedex.repository.PokemonRepository
import dagger.Reusable
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@Reusable
class GetPokemonDetailUseCase @Inject constructor(
    private val repo: PokemonRepository
) {
    operator fun invoke(id: Int): Flow<Pokemon> = repo.getPokemonFullDetail(id)
}
