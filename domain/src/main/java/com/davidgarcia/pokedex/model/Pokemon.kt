package com.davidgarcia.pokedex.model

data class Pokemon(
    val id: Int,
    val name: String,
    val imageUrl: String? = null,
    val types: List<String> = emptyList(),
    val weight: Int = 0,
    val height: Int = 0,
    val description: String = "",
    val color: String = "",
    val stats: Map<String, Int> = emptyMap()
)
