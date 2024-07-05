package com.ujizin.pokedex.presentation.list.components

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import com.ujizin.pokedex.domain.Pokemon
import com.ujizin.pokedex.presentation.navigation.LocalSharedTransitionScope
import com.ujizin.pokedex.presentation.themes.PokedexTheme
import com.ujizin.pokedex.presentation.utils.shape.TrapezeShape
import com.ujizin.pokedex.presentation.utils.shape.trapezeBorder
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterialApi::class, ExperimentalSharedTransitionApi::class)
@Composable
fun PokemonCardItem(
    modifier: Modifier = Modifier,
    pokemon: Pokemon,
    animatedContentScope: AnimatedContentScope,
    onPokemonClick: (Pokemon) -> Unit,
) {
    val context = LocalPlatformContext.current
    val state = remember(pokemon) {
        MutableTransitionState(false).apply { targetState = true }
    }

    AnimatedVisibility(
        modifier = modifier,
        visibleState = state,
        enter = fadeIn() + slideInHorizontally { it / 2 }
    ) {
        with(LocalSharedTransitionScope.current) {
            Card(
                shape = RoundedCornerShape(16.dp, 0.dp, 0.dp, 16.dp),
                onClick = { onPokemonClick(pokemon) }
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    AsyncImage(
                        modifier = Modifier
                            .background(
                                MaterialTheme.colors.secondary,
                                shape = TrapezeShape
                            )
                            .trapezeBorder(4.dp, Color.Black)
                            .padding(vertical = 4.dp, horizontal = 16.dp)
                            .padding(end = 16.dp)
                            .size(96.dp)
                            .weight(1F)
                            .sharedElement(
                                state = rememberSharedContentState(key = pokemon.capitalizedName),
                                animatedVisibilityScope = animatedContentScope,
                            ),
                        model = pokemon.imageUrl,
                        contentDescription = null,
                        imageLoader = ImageLoader.Builder(context).build()
                    )
                    Column(
                        modifier = Modifier.weight(1F).padding(start = 16.dp),
                    ) {
                        Text(
                            text = "#${pokemon.id}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                        )
                        Text(
                            modifier = Modifier.widthIn(max = 120.dp),
                            text = pokemon.capitalizedName,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun PokemonCardItemPreview() {
    PokedexTheme {
        PokemonCardItemPreview()
    }
}