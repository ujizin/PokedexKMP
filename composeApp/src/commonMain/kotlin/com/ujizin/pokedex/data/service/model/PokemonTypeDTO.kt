package com.ujizin.pokedex.data.service.model

import kotlinx.serialization.Serializable

@Serializable
data class PokemonTypeDTO(
    val type: Type
) {
    @Serializable
    data class Type(
        val name: String
    )
}
