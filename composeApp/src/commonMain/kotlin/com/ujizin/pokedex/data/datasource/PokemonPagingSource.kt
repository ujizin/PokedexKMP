package com.ujizin.pokedex.data.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ujizin.pokedex.data.service.PokemonService
import com.ujizin.pokedex.data.service.model.PokemonDTO
import kotlinx.coroutines.flow.first

class PokemonPagingSource(
    private val pokemonService: PokemonService,
) : PagingSource<String, PokemonDTO>() {

    private var pagingKey = FIRST_PAGE_POKEMON_URL

    override fun getRefreshKey(state: PagingState<String, PokemonDTO>): String = pagingKey

    override suspend fun load(params: LoadParams<String>): LoadResult<String, PokemonDTO> {
        pagingKey = params.key ?: FIRST_PAGE_POKEMON_URL

        try {
            val pokemons = pokemonService.fetchPokemons(pagingKey).first()

            return LoadResult.Page(
                data = pokemons.results,
                prevKey = pagingKey.takeUnless { it == FIRST_PAGE_POKEMON_URL },
                nextKey = pokemons.next
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    companion object {
        private const val FIRST_PAGE_POKEMON_URL = "https://pokeapi.co/api/v2/pokemon"
    }
}
