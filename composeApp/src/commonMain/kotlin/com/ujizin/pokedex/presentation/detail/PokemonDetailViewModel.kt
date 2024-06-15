package com.ujizin.pokedex.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ujizin.pokedex.data.repository.PokemonRepository
import com.ujizin.pokedex.presentation.navigation.Destination.PokemonDetail.Companion.NAME_ARG
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class PokemonDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: PokemonRepository
) : ViewModel() {

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState = savedStateHandle.getStateFlow(NAME_ARG, "")
        .flatMapConcat { name ->
            require(name.isNotBlank()) { "Pokemon name must be passed" }
            repository.getPokemon(name)
        }
        .map { pokemon -> PokemonDetailUiState(pokemon = pokemon, isLoading = false) }
        .catch { exception ->
            // TODO("Handle error, not implemented yet")
            throw exception
        }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            PokemonDetailUiState()
        )
}
