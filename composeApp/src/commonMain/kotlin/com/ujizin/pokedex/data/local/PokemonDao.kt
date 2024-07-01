package com.ujizin.pokedex.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao {

    @Query("SELECT * FROM pokemon")
    fun pagingSource(): PagingSource<Int, PokemonEntity>

    @Query("SELECT * FROM pokemon where name = :name")
    fun getPokemon(name: String): Flow<PokemonEntity>

    @Upsert
    suspend fun upsertAll(pokemons: List<PokemonEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pokemon: PokemonEntity)

    @Transaction
    suspend fun withTransaction(block: suspend PokemonDao.() -> Unit) {
        block()
    }
}
