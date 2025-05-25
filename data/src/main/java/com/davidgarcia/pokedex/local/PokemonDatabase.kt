package com.davidgarcia.pokedex.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.davidgarcia.pokedex.local.dao.PokemonDao
import com.davidgarcia.pokedex.local.model.PokemonEntity

@Database(
    entities = [PokemonEntity::class],
    version = 1
)
abstract class PokemonDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
}
