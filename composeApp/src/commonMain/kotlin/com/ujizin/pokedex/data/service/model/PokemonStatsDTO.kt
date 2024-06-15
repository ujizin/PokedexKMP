package com.ujizin.pokedex.data.service.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonStatsDTO(
    @SerialName("base_stat")
    val baseStat: Int,
    val stat: PokemonStatDTO
) {

    @Serializable
    data class PokemonStatDTO(
        val name: String,
    )
}
