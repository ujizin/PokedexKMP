package com.ujizin.pokedex.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.ujizin.pokedex.data.repository.PokemonRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

class PokemonListViewModel(
    private val repository: PokemonRepository
) : ViewModel() {

    val uiState: StateFlow<PokemonListUiState> = flow {
        val pagingFlow = repository.getPokemonPaging().cachedIn(viewModelScope)
        emit(PokemonListUiState(pagingFlow))
    }.stateIn(viewModelScope, SharingStarted.Eagerly, PokemonListUiState())
}
