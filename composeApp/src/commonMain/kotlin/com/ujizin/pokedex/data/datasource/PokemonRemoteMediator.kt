package com.ujizin.pokedex.data.datasource

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.ujizin.pokedex.data.local.PokemonDao
import com.ujizin.pokedex.data.local.PokemonEntity
import com.ujizin.pokedex.data.mapper.PokemonMapper
import com.ujizin.pokedex.data.service.PokemonService
import kotlinx.coroutines.flow.first

@OptIn(ExperimentalPagingApi::class)
class PokemonRemoteMediator(
    private val pokemonService: PokemonService,
    private val pokemonDao: PokemonDao,
    private val pokemonMapper: PokemonMapper,
) : RemoteMediator<Int, PokemonEntity>() {

    private var pagingKey = FIRST_PAGE_POKEMON_URL

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.SKIP_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PokemonEntity>
    ): MediatorResult = when (loadType) {
        LoadType.PREPEND -> MediatorResult.Success(endOfPaginationReached = true)
        else -> try {
            val pokemonPage = pokemonService.fetchPokemons(pagingKey).first().apply {
                pagingKey = next ?: FIRST_PAGE_POKEMON_URL
                pokemonDao.withTransaction {
                    upsertAll(results.map(pokemonMapper::fromPageRemoteToEntity))
                }
            }

            MediatorResult.Success(endOfPaginationReached = pokemonPage.next.isNullOrBlank())
        } catch (exception: Exception) {
            MediatorResult.Error(exception)
        }
    }

    companion object {
        private const val FIRST_PAGE_POKEMON_URL = "https://pokeapi.co/api/v2/pokemon"
    }
}
