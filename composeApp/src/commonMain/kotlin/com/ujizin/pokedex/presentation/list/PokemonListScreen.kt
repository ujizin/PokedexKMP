package com.ujizin.pokedex.presentation.list

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import app.cash.paging.compose.collectAsLazyPagingItems
import com.ujizin.pokedex.domain.Pokemon
import com.ujizin.pokedex.presentation.list.components.PokemonCardItem
import com.ujizin.pokedex.presentation.themes.PokedexTheme
import com.ujizin.pokedex.presentation.utils.collectAsStateMultiplatform
import com.ujizin.pokedex.presentation.utils.rememberCurrentOffset
import kotlinx.coroutines.flow.Flow
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import pokedexkmp.composeapp.generated.resources.Res
import pokedexkmp.composeapp.generated.resources.pokeball

@OptIn(KoinExperimentalAPI::class)
@Composable
fun PokemonListScreen(
    modifier: Modifier = Modifier,
    viewModel: PokemonListViewModel = koinViewModel(),
    onPokemonClick: (String) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateMultiplatform()

    PokemonListContent(
        modifier = modifier,
        uiState = uiState,
        onPokemonClick = onPokemonClick,
    )
}

@Composable
fun PokemonListContent(
    modifier: Modifier = Modifier,
    uiState: PokemonListUiState,
    onPokemonClick: (String) -> Unit,
) {
    Box(modifier) {
        val scrollState = rememberLazyListState()
        val scrollPosition by rememberCurrentOffset(scrollState)
        val rotateAnimated by animateFloatAsState(scrollPosition / 10F)

        PokeballBackground(
            modifier = Modifier.align(Alignment.CenterStart),
            rotation = rotateAnimated,
        )

        PokemonListContainer(
            modifier = Modifier.fillMaxSize(),
            scrollState = scrollState,
            pokemonPagerFlow = uiState.pagingFlow,
            onPokemonClick = onPokemonClick,
        )

    }
}

@Composable
fun PokeballBackground(
    rotation: Float,
    modifier: Modifier = Modifier
) {
    val density = LocalDensity.current
    var width by remember { mutableStateOf(0.dp) }
    Image(
        modifier = Modifier
            .fillMaxSize()
            .onPlaced { width = with(density) { it.size.width.toDp() } }
            .offset(x = -(width / 2F))
            .scale(1.25F)
            .rotate(rotation)
            .then(modifier),
        painter = painterResource(Res.drawable.pokeball),
        contentDescription = null,
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun PokemonListContainer(
    scrollState: LazyListState,
    pokemonPagerFlow: Flow<PagingData<Pokemon>>,
    onPokemonClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val pokemonList = pokemonPagerFlow.collectAsLazyPagingItems()

    LazyColumn(
        state = scrollState,
        modifier = modifier,
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        stickyHeader {
            TopAppBar(
                windowInsets = WindowInsets(top = 16.dp),
                title = { Text("Pokedex Multiplatform") }
            )
        }
        item { Spacer(Modifier.height(64.dp)) }
        items(pokemonList.itemCount) { index ->
            val pokemon = pokemonList[index]!!
            PokemonCardItem(
                modifier = Modifier
                    .width(IntrinsicSize.Max)
                    .animateItemPlacement(),
                pokemon = pokemon,
                onPokemonClick = { onPokemonClick(it.name) }
            )
        }
        pokemonList.loadState.apply {
            if (refresh is LoadState.Loading || append is LoadState.Loading) {
                item {
                    Box(
                        modifier = Modifier.width(240.dp).height(120.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        CircularProgressIndicator(modifier = Modifier.size(32.dp))
                    }
                }
            }
        }
        item { Spacer(Modifier.height(96.dp)) }
    }
}

@Composable
@Preview
private fun PokemonListContentPreview() {
    PokedexTheme {
        PokemonListContent(
            uiState = PokemonListUiState(),
            onPokemonClick = {}
        )
    }
}
