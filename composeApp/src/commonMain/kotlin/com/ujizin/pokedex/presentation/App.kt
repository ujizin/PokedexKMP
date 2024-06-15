package com.ujizin.pokedex.presentation

import androidx.compose.runtime.Composable
import com.ujizin.pokedex.presentation.navigation.PokedexNavigation
import com.ujizin.pokedex.presentation.themes.PokedexTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    PokedexTheme {
        PokedexNavigation()
    }
}
