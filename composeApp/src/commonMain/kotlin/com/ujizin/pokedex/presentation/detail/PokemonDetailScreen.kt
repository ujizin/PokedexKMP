package com.ujizin.pokedex.presentation.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ujizin.pokedex.presentation.themes.PokedexTheme
import com.ujizin.pokedex.presentation.detail.components.PokemonDetailContainer
import com.ujizin.pokedex.presentation.detail.components.PokemonDetailLoading
import com.ujizin.pokedex.presentation.utils.collectAsStateMultiplatform
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.parameter.ParametersHolder


@OptIn(KoinExperimentalAPI::class)
@Composable
fun PokemonDetailScreen(
    onBackPress: () -> Unit,
    modifier: Modifier = Modifier,
    parametersHolder: ParametersHolder,
    viewModel: PokemonDetailViewModel = koinViewModel(parameters = { parametersHolder }),
) {
    val uiState by viewModel.uiState.collectAsStateMultiplatform()

    PokemonDetailContent(
        modifier = modifier,
        uiState = uiState,
        onBackPress = onBackPress,
    )
}

@Composable
fun PokemonDetailContent(
    uiState: PokemonDetailUiState, onBackPress: () -> Unit, modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TopAppBar(
            windowInsets = WindowInsets(top = 16.dp, left = 8.dp),
            title = {
                when {
                    uiState.pokemon != null -> Text(uiState.pokemon.capitalizedName)
                    uiState.isLoading -> LinearProgressIndicator(color = Color.White)
                }
            },
            navigationIcon = {
                IconButton(onClick = onBackPress) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null,
                    )
                }
            },
        )
        when {
            uiState.isLoading -> PokemonDetailLoading()
            uiState.pokemon != null -> PokemonDetailContainer(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(top = 16.dp)
                    .padding(horizontal = 24.dp),
                pokemon = uiState.pokemon,
            )
        }
    }
}

@Composable
@Preview
fun PokemonDetailContentPreview() {
    PokedexTheme {
        PokemonDetailContent(uiState = PokemonDetailUiState(), onBackPress = {})
    }
}