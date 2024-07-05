package com.ujizin.pokedex.presentation.navigation

import com.ujizin.pokedex.domain.Pokemon
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object Destination {
    @Serializable
    data object PokemonList

    @Serializable
    data class PokemonDetail(val pokemon: String) {
        companion object {
            fun setDestination(pokemon: Pokemon) = PokemonDetail(Json.encodeToString(pokemon))
        }
    }
}
