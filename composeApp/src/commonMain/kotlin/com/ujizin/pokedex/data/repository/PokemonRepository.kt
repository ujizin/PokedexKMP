package com.ujizin.pokedex.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.map
import com.ujizin.pokedex.data.datasource.PokemonPagingSource
import com.ujizin.pokedex.data.mapper.PokemonMapper
import com.ujizin.pokedex.data.service.PokemonService
import com.ujizin.pokedex.domain.Pokemon
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class PokemonRepository(
    private val pokemonService: PokemonService,
    private val pokemonPagingSource: PokemonPagingSource,
    private val pokemonMapper: PokemonMapper,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    fun getPokemonPaging() = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = ::pokemonPagingSource,
    ).flow.map { pagingData ->
        pagingData.map(pokemonMapper::toDomain)
    }.flowOn(dispatcher)

    fun getPokemon(
        name: String
    ): Flow<Pokemon> = pokemonService.fetchPokemon(name)
        .map(pokemonMapper::toDomain)
        .flowOn(dispatcher)
}
