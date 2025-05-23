package com.davidgarcia.pokedex.repository

import com.davidgarcia.pokedex.di.DispatcherProvider
import com.davidgarcia.pokedex.local.dao.PokemonDao
import com.davidgarcia.pokedex.local.model.PokemonEntity
import com.davidgarcia.pokedex.model.Pokemon
import com.davidgarcia.pokedex.remote.PokeApiService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val api: PokeApiService,
    private val dao: PokemonDao,
    private val dispatchers: DispatcherProvider
): PokemonRepository {

    /**
     * Emits each Pokemon as it is loaded. UI can update step by step.
     * Uses injected IO dispatcher
     * */
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getPokemonList(): Flow<Pokemon> = flow {

        // Emits cached
        // Emit cached once from local DB
        val localList = dao.getAll()
            .flowOn(dispatchers.io)
            .first()
        localList.forEach { e -> emit(Pokemon(e.id, e.name, e.imageUrl)) }


        // Fetches network and save locally
        val generation = api.getGeneration(FIRST_GENERATION_ID)
        generation.pokemonSpecies
            .asFlow()
            .flatMapMerge(concurrency = FLOWS) { res ->
                flow {
                    val id = res.url.trimEnd('/').substringAfterLast('/').toInt()
                    // Fetch species names and details in parallel
                    val specDeferred = coroutineScope { async { api.getPokemonSpecies(id) } }
                    val detailDeferred = coroutineScope { async { api.getPokemonDetail(id) } }

                    val spec = specDeferred.await()
                    val detail = detailDeferred.await()

                    val name = spec.names.firstOrNull { it.language.name == SPANISH_CODE }?.name
                        ?: spec.names.first { it.language.name == ENGLISH_CODE }.name

                    val imageUrl = detail.sprites.other.home.frontDefault

                    val entity = PokemonEntity(id, name, imageUrl)

                    dao.insertAll(listOf(entity))
                    emit(Pokemon(id, name, imageUrl))
                }
            }
            .collect { emit(it) }
    }.flowOn(dispatchers.io)


    /**
     * Searches local database by id or name, returns full matches
     * */
    override fun searchPokemon(query: String): Flow<List<Pokemon>> =
        dao.search(query.ifBlank { "" })
            .map { list -> list.map { e -> Pokemon(e.id, e.name, e.imageUrl) } }
            .flowOn(dispatchers.io)

    companion object {
        const val FIRST_GENERATION_ID = 1
        const val FLOWS = 5
        const val SPANISH_CODE = "es"
        const val ENGLISH_CODE = "en"
    }
}
