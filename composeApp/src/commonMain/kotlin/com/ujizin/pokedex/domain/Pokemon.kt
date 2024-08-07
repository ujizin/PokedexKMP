package com.ujizin.pokedex.domain

import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.LocaleList
import kotlinx.serialization.Serializable

@Serializable
data class Pokemon(
    val id: Int,
    val name: String,
    val types: List<PokemonType>,
    val imageUrl: String,
    val stats: List<Stats>,
    val specialStats: List<Stats>,
) {

    val capitalizedName: String
        get() = name.capitalize(LocaleList.current)

    @Serializable
    data class Stats(
        val name: String,
        val base: Int,
    )
}
