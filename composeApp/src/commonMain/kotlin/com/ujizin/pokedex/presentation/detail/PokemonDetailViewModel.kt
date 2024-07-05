package com.ujizin.pokedex.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ujizin.pokedex.data.repository.PokemonRepository
import com.ujizin.pokedex.domain.Pokemon
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.serialization.json.Json

class PokemonDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: PokemonRepository
) : ViewModel() {

    private val pokemon: Pokemon = savedStateHandle.get<String>("pokemon").run {
        Json.decodeFromString(this!!)
    }

    val uiState: StateFlow<PokemonDetailUiState>
        field = MutableStateFlow(PokemonDetailUiState(pokemon = pokemon))

    fun getPokemon() {
        repository.getPokemon(pokemon.name).onStart {
            uiState.update { PokemonDetailUiState(pokemon = pokemon) }
        }.onEach { pokemon ->
            uiState.update { PokemonDetailUiState(pokemon = pokemon, isLoading = false) }
        }.catch {
            uiState.update { PokemonDetailUiState(isError = true, isLoading = false) }
        }.launchIn(viewModelScope)
    }
}
