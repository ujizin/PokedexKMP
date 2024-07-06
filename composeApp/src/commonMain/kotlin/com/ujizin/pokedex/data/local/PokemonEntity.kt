package com.ujizin.pokedex.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import kotlinx.serialization.Serializable

@Entity(tableName = "pokemon")
data class PokemonEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    @ColumnInfo("image_url")
    val imageUrl: String,
    @field:TypeConverters(ListStringConverter::class)
    val types: List<String> = emptyList(),
    @field:TypeConverters(PokemonTypeConverter::class)
    val stats: List<Stats> = emptyList(),
    @field:TypeConverters(PokemonTypeConverter::class)
    @ColumnInfo("special_stats")
    val specialStats: List<Stats> = emptyList(),
) {

    val isDetailCached: Boolean
        get() = types.isNotEmpty() || stats.isNotEmpty() || specialStats.isNotEmpty()

    @Serializable
    data class Stats(
        val name: String,
        val base: Int,
    )
}
