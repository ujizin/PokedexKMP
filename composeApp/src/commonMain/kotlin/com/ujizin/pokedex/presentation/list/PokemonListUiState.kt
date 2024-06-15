package com.ujizin.pokedex.presentation.list

import androidx.compose.runtime.Immutable
import androidx.paging.PagingData
import com.ujizin.pokedex.domain.Pokemon
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Immutable
data class PokemonListUiState(
    val pagingFlow: Flow<PagingData<Pokemon>> = emptyFlow(),
)
