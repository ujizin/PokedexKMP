package com.ujizin.pokedex.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ujizin.pokedex.data.repository.PokemonRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PokemonListViewModel(
    private val repository: PokemonRepository
) : ViewModel() {

    val uiState: StateFlow<PokemonListUiState>
        field = MutableStateFlow(PokemonListUiState())

    fun fetchPokemons() {
        viewModelScope.launch {
            uiState.update {
                it.copy(pagingFlow = repository.getPokemonPaging())
            }
        }
    }
}
