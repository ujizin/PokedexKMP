package com.ujizin.pokedex.data.local

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class PokemonTypeConverter {

    @TypeConverter
    fun convertToJsonString(stats: List<PokemonEntity.Stats>) = Json.encodeToString(stats)

    @TypeConverter
    fun convertToStats(json: String): List<PokemonEntity.Stats> = Json.decodeFromString(json)

}