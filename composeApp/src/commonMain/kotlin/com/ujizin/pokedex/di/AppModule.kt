package com.ujizin.pokedex.di

import com.ujizin.pokedex.data.mapper.PokemonMapper
import com.ujizin.pokedex.data.repository.PokemonRepository
import com.ujizin.pokedex.data.service.PokemonService
import com.ujizin.pokedex.data.service.core.ServiceFactory
import com.ujizin.pokedex.presentation.list.PokemonListViewModel
import com.ujizin.pokedex.presentation.detail.PokemonDetailViewModel
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

object AppModule {

    private val dataModules = module {
        singleOf(::PokemonMapper)
        single { ServiceFactory.client.create<PokemonService>() }
        single { PokemonRepository(get(), get()) }
    }

    private val presentationModules = module {
        viewModelOf(::PokemonListViewModel)
        viewModelOf(::PokemonDetailViewModel)
    }

    val appModules = listOf(presentationModules, dataModules)
}
