package com.ujizin.pokedex.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ujizin.pokedex.data.repository.PokemonRepository
import com.ujizin.pokedex.presentation.navigation.Destination.PokemonDetail.Companion.NAME_ARG
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update

class PokemonDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: PokemonRepository
) : ViewModel() {

    private val pokemonName = savedStateHandle.get<String?>(NAME_ARG).orEmpty()

    val uiState: StateFlow<PokemonDetailUiState>
        field = MutableStateFlow(PokemonDetailUiState())

    fun getPokemon() {
        repository.getPokemon(pokemonName).onStart {
            require(pokemonName.isNotBlank()) { "Pokemon name must be passed" }
            uiState.update { PokemonDetailUiState() }
        }.onEach { pokemon ->
            uiState.update { PokemonDetailUiState(pokemon = pokemon, isLoading = false) }
        }.catch {
            uiState.update { PokemonDetailUiState(isError = true, isLoading = false) }
        }.launchIn(viewModelScope)
    }
}
