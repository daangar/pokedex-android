package com.davidgarcia.pokedex.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NamedAPIResource(
    val name: String,
    val url: String
)

@JsonClass(generateAdapter = true)
data class GenerationResponse(
    val id: Int,
    @Json(name = "pokemon_species")
    val pokemonSpecies: List<NamedAPIResource>
)

@JsonClass(generateAdapter = true)
data class NameEntry(
    val name: String,
    val language: NamedAPIResource
)

@JsonClass(generateAdapter = true)
data class PokemonSpeciesResponse(
    val id: Int,
    val names: List<NameEntry>
)

@JsonClass(generateAdapter = true)
data class PokemonDetailResponse(
    val sprites: Sprites
)

@JsonClass(generateAdapter = true)
data class Sprites(
    val other: Other
)

@JsonClass(generateAdapter = true)
data class Other(
    val home: Home
)

@JsonClass(generateAdapter = true)
data class Home(
    @Json(name = "front_default")
    val frontDefault: String?
)