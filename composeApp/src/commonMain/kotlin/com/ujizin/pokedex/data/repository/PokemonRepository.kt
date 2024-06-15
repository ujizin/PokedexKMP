package com.ujizin.pokedex.data.repository

import com.ujizin.pokedex.data.mapper.PokemonMapper
import com.ujizin.pokedex.data.service.PokemonService
import com.ujizin.pokedex.domain.Pokemon
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class PokemonRepository(
    private val pokemonService: PokemonService,
    private val pokemonMapper: PokemonMapper,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    fun getPokemons(next: String): Flow<List<Pokemon>> = pokemonService.fetchPokemons(next)
        .map { page -> page.results }
        .map(pokemonMapper::toDomain)
        .flowOn(dispatcher)

    fun getPokemon(
        name: String
    ): Flow<Pokemon> = pokemonService.fetchPokemon(name)
        .map(pokemonMapper::toDomain)
        .flowOn(dispatcher)
}
