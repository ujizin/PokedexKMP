package com.ujizin.pokedex.data.local

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class ListStringConverter {

    @TypeConverter
    fun convertToJsonString(stats: List<String>) = Json.encodeToString(stats)

    @TypeConverter
    fun convertToStats(json: String): List<String> = Json.decodeFromString(json)

}