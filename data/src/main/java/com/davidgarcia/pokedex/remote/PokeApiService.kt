package com.davidgarcia.pokedex.remote

import com.davidgarcia.pokedex.remote.model.GenerationResponse
import com.davidgarcia.pokedex.remote.model.PokemonDetailResponse
import com.davidgarcia.pokedex.remote.model.PokemonSpeciesResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface PokeApiService {
    // Get generation data with Pokemon species list
    @GET("generation/{id}")
    suspend fun getGeneration(@Path("id") id: Int): GenerationResponse

    // Get details for a Pokemon species by id
    @GET("pokemon-species/{id}")
    suspend fun getPokemonSpecies(@Path("id") id: Int): PokemonSpeciesResponse

    // Get pokemon details
    @GET("pokemon/{id}")
    suspend fun getPokemonDetail(@Path("id") id: Int): PokemonDetailResponse
}
