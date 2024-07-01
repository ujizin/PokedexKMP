package com.ujizin.pokedex.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.map
import com.ujizin.pokedex.data.datasource.PokemonRemoteMediator
import com.ujizin.pokedex.data.local.PokemonDao
import com.ujizin.pokedex.data.mapper.PokemonMapper
import com.ujizin.pokedex.data.service.PokemonService
import com.ujizin.pokedex.domain.Pokemon
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach

class PokemonRepository(
    private val pokemonService: PokemonService,
    private val pokemonRemoteMediator: PokemonRemoteMediator,
    private val pokemonDao: PokemonDao,
    private val pokemonMapper: PokemonMapper,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    @OptIn(ExperimentalPagingApi::class)
    fun getPokemonPaging() = Pager(
        config = PagingConfig(pageSize = 20),
        remoteMediator = pokemonRemoteMediator,
        pagingSourceFactory = pokemonDao::pagingSource,
    ).flow.map { pagingData ->
        pagingData.map(pokemonMapper::fromEntityToDomain)
    }.flowOn(dispatcher)

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getPokemon(
        name: String
    ): Flow<Pokemon> = pokemonDao.getPokemon(name)
        .flatMapConcat { entity ->
            when {
                entity.isDetailCached -> flowOf(pokemonMapper.fromEntityToDomain(entity))
                else -> pokemonService.fetchPokemon(name)
                    .map(pokemonMapper::fromRemoteToDomain)
                    .onEach { pokemonDao.insert(pokemonMapper.fromDomainToEntity(it)) }
            }
        }
        .flowOn(dispatcher)
}
