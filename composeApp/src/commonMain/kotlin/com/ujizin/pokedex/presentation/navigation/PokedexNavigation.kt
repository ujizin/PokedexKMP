package com.ujizin.pokedex.presentation.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ujizin.pokedex.presentation.detail.PokemonDetailScreen
import com.ujizin.pokedex.presentation.list.PokemonListScreen
import com.ujizin.pokedex.presentation.navigation.Destination.PokemonDetail
import com.ujizin.pokedex.presentation.navigation.Destination.PokemonList
import org.koin.core.parameter.parametersOf

@Composable
fun PokedexNavigation() {
    val navController = rememberNavController()
    PokedexSharedTransitionLayout {
        NavHost(navController = navController, startDestination = PokemonList) {
            composable<PokemonList>(exitTransition = { slideOutHorizontally { -it } }) {
                PokemonListScreen(
                    animatedContentScope = this,
                    onPokemonClick = { pokemon ->
                        navController.navigate(PokemonDetail.setDestination(pokemon))
                    },
                )
            }
            composable<PokemonDetail>(enterTransition = { slideInHorizontally { it } }) {
                PokemonDetailScreen(
                    modifier = Modifier.fillMaxSize(),
                    animatedContentScope = this,
                    onBackPress = navController::navigateUp,
                    // Temporally fix for issue: https://github.com/InsertKoinIO/koin/issues/1878
                    parametersHolder = parametersOf(
                        SavedStateHandle.createHandle(
                            null,
                            it.arguments
                        )
                    )
                )
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
val LocalSharedTransitionScope =
    compositionLocalOf<SharedTransitionScope> { throw Exception("Not set") }

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun PokedexSharedTransitionLayout(block: @Composable () -> Unit) {
    SharedTransitionLayout {
        CompositionLocalProvider(LocalSharedTransitionScope provides this) {
            block()
        }
    }
}