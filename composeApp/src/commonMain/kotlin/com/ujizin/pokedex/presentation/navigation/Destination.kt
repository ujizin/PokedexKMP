package com.ujizin.pokedex.presentation.navigation

sealed class Destination(val route: String) {
    data object PokemonList : Destination("/")
    data class PokemonDetail(val name: String = "{$NAME_ARG}") : Destination("/$name") {
        companion object {
            const val NAME_ARG = "id"
        }
    }
}
