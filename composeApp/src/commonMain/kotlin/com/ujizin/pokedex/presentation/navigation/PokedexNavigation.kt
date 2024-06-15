package com.ujizin.pokedex.presentation.navigation

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ujizin.pokedex.presentation.detail.PokemonDetailScreen
import com.ujizin.pokedex.presentation.list.PokemonListScreen
import com.ujizin.pokedex.presentation.navigation.Destination.PokemonList
import com.ujizin.pokedex.presentation.navigation.Destination.PokemonDetail
import io.ktor.http.parametersOf
import org.koin.core.parameter.parametersOf

@Composable
fun PokedexNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = PokemonList.route,
    ) {
        composable(
            route = PokemonList.route,
            exitTransition = { slideOutHorizontally { -it } },
        ) {
            PokemonListScreen(
                onPokemonClick = { pokemon ->
                    navController.navigate(PokemonDetail(pokemon).route)
                },
            )
        }
        composable(
            route = PokemonDetail().route,
            arguments = listOf(
                navArgument(PokemonDetail.NAME_ARG) { type = NavType.StringType }
            ),
            enterTransition = { slideInHorizontally { it } },
        ) {
            PokemonDetailScreen(
                modifier = Modifier.fillMaxSize(),
                onBackPress = navController::navigateUp,
                // Temporally fix for issue: https://github.com/InsertKoinIO/koin/issues/1878
                parametersHolder = parametersOf(SavedStateHandle.createHandle(null, it.arguments))
            )
        }
    }
}
