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
data class PokemonTypeEntry(
    val slot: Int,
    val type: NamedAPIResource
)

@JsonClass(generateAdapter = true)
data class PokemonStatsEntry(
    @Json(name = "base_stat")
    val baseStat: Int,
    val stat: NamedAPIResource
)

@JsonClass(generateAdapter = true)
data class FlavorText(
    @Json(name = "flavor_text")
    val flavorText: String,
    val language: NamedAPIResource
)

@JsonClass(generateAdapter = true)
data class PokemonSpeciesResponse(
    val id: Int,
    val names: List<NameEntry>,
    val color: NamedAPIResource,
    @Json(name = "flavor_text_entries")
    val flavorTextEntries: List<FlavorText>
)

@JsonClass(generateAdapter = true)
data class PokemonDetailResponse(
    val id: Int,
    val name: String,
    val types: List<PokemonTypeEntry>,
    val stats: List<PokemonStatsEntry>,
    val weight: Int,
    val height: Int,
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