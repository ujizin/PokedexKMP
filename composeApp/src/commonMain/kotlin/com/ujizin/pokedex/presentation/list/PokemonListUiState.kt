package com.ujizin.pokedex.presentation.list

import androidx.compose.runtime.Immutable
import com.ujizin.pokedex.domain.Pokemon

@Immutable
data class PokemonListUiState(
    val pokemons: List<Pokemon> = emptyList(),
    val isLoading: Boolean = true,
)
