package com.davidgarcia.pokedex.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.davidgarcia.pokedex.local.model.PokemonEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao {

    @Query("SELECT * FROM pokemon ORDER by id")
    fun getAll(): Flow<List<PokemonEntity>>

    @Query("SELECT * FROM pokemon where id = :query OR name LIKE '%' || :query || '%' ORDER by id")
    fun search(query: String): Flow<List<PokemonEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(pokemon: List<PokemonEntity>)
} 