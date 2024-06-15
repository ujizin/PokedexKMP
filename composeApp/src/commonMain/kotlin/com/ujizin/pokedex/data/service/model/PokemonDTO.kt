package com.ujizin.pokedex.data.service.model

import kotlinx.serialization.Serializable

@Serializable
data class PokemonDTO(
    val name: String,
    val url: String = String(),
    val id: Int? = null,
    val types: List<PokemonTypeDTO> = emptyList(),
    val stats: List<PokemonStatsDTO> = emptyList()
) {

    val index: Int
        get() = id ?: url.split("/".toRegex()).dropLast(1).last().toInt()

    val imageUrl: String
        get() {
            return StringBuilder()
                .append("https://raw.githubusercontent.com")
                .append("/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork")
                .append("/$index.png")
                .toString()
        }
}
