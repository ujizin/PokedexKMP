package com.ujizin.pokedex.data.service

import com.ujizin.pokedex.data.service.model.PokemonDTO
import com.ujizin.pokedex.data.service.model.PokemonPageDTO
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Path
import de.jensklingenberg.ktorfit.http.Url
import kotlinx.coroutines.flow.Flow

interface PokemonService {

    @GET
    fun fetchPokemons(@Url url: String): Flow<PokemonPageDTO>

    @GET
    fun fetchPokemonDetail(@Url url: String): Flow<PokemonDTO>

    @GET("pokemon/{name}")
    fun fetchPokemon(@Path("name") name: String): Flow<PokemonDTO>
}
