package com.ujizin.pokedex.presentation.detail

import androidx.compose.runtime.Immutable
import com.ujizin.pokedex.domain.Pokemon

@Immutable
data class PokemonDetailUiState(
    val pokemon: Pokemon? = null,
    val isLoading: Boolean = true,
)
