package com.ujizin.pokedex.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ujizin.pokedex.data.repository.PokemonRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update

class PokemonListViewModel(
    private val repository: PokemonRepository
) : ViewModel() {

    val uiState: StateFlow<PokemonListUiState>
        field = MutableStateFlow(PokemonListUiState())

    fun fetchPokemons(nextUrl: String? = null) {
        repository.getPokemons(nextUrl ?: POKEMON_URL)
            .onStart { uiState.update { it.copy(isLoading = true) } }
            .onEach { pokemons ->
                uiState.update { state ->
                    state.copy(
                        isLoading = false, pokemons = state.pokemons + pokemons,
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    companion object {
        private const val POKEMON_URL = "https://pokeapi.co/api/v2/pokemon"
    }
}
