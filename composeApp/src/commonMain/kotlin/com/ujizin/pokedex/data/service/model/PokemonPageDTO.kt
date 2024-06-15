package com.ujizin.pokedex.data.service.model

import kotlinx.serialization.Serializable

@Serializable
data class PokemonPageDTO(
    val next: String?,
    val results: List<PokemonDTO>
)
