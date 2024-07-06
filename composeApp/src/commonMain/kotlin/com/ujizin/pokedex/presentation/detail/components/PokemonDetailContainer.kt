package com.ujizin.pokedex.presentation.detail.components

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.LocaleList
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import com.ujizin.pokedex.domain.Pokemon
import com.ujizin.pokedex.domain.PokemonType
import com.ujizin.pokedex.presentation.navigation.LocalSharedTransitionScope
import com.ujizin.pokedex.presentation.utils.color
import com.ujizin.pokedex.presentation.utils.toColor
import kotlinx.coroutines.delay

@Composable
fun PokemonDetailContainer(
    pokemon: Pokemon,
    isLoading: Boolean,
    animatedContentScope: AnimatedContentScope,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.animateContentSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        PokemonHeader(
            id = pokemon.id,
            name = pokemon.capitalizedName,
            animatedContentScope = animatedContentScope,
            imageUrl = pokemon.imageUrl,
        )

        if (isLoading) {
            PokemonDetailLoading(Modifier.padding(top = 24.dp))
            return
        }

        PokemonTypes(
            modifier = Modifier.padding(top = 16.dp),
            types = pokemon.types,
        )
        Divider(
            modifier = Modifier.padding(vertical = 24.dp),
            color = Color.LightGray,
        )

        PokemonStatContent(
            title = "Base Stats",
            stats = pokemon.stats,
        )
        Divider(
            modifier = Modifier.padding(vertical = 24.dp),
            color = Color.LightGray,
        )
        PokemonStatContent(
            title = "Special Stats",
            stats = pokemon.specialStats,
        )
        Spacer(Modifier.height(64.dp))
    }
}

@Composable
private fun PokemonStatContent(
    modifier: Modifier = Modifier,
    title: String,
    stats: List<Pokemon.Stats>,
) {
    Column(modifier) {
        val state = remember { MutableTransitionState(false) }.apply { targetState = true }
        AnimatedVisibility(
            visibleState = state,
            enter = fadeIn() + slideInHorizontally(),
        ) {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
            )
        }
        Spacer(Modifier.height(24.dp))
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            val delay by remember { mutableStateOf(50L) }
            stats.forEachIndexed { index, stat ->
                val statState = remember { MutableTransitionState(false) }

                LaunchedEffect(Unit) {
                    delay(delay * index)
                    statState.targetState = true
                }

                AnimatedVisibility(
                    visibleState = statState,
                    enter = fadeIn() + slideInHorizontally()
                ) {
                    PokemonStat(stat)
                }
            }
        }
    }
}

@Composable
fun PokemonStat(
    stat: Pokemon.Stats,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.widthIn(min = 32.dp),
            textAlign = TextAlign.Center,
            text = stat.name.capitalize(LocaleList.current),
            fontWeight = FontWeight.Bold
        )
        BoxWithConstraints(Modifier.weight(1F)) {
            val percentage = stat.base / 100F
            Box(
                Modifier
                    .size(maxWidth * percentage, 20.dp)
                    .background(stat.toColor(), RoundedCornerShape(6.dp))
            )
        }
    }
}

@Composable
fun PokemonTypes(
    types: List<PokemonType>,
    modifier: Modifier = Modifier,
) {
    val state = remember { MutableTransitionState(false) }.apply {
        targetState = true
    }
    AnimatedVisibility(
        visibleState = state,
        enter = fadeIn() + slideInVertically { it },
    ) {
        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally)
        ) {

            types.forEach { type ->
                Text(
                    modifier = Modifier
                        .background(type.color, RoundedCornerShape(16.dp))
                        .padding(vertical = 4.dp, horizontal = 12.dp),
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    text = type.name.capitalize(LocaleList.current),
                )
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun PokemonHeader(
    id: Int,
    name: String,
    imageUrl: String,
    animatedContentScope: AnimatedContentScope,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val localSharedTransitionScope = LocalSharedTransitionScope.current
        with(localSharedTransitionScope) {
            PokemonImage(
                modifier = Modifier.size(180.dp).sharedElement(
                    state = rememberSharedContentState(key = name),
                    animatedVisibilityScope = animatedContentScope,
                ),
                imageUrl = imageUrl
            )
        }
        Spacer(Modifier.height(8.dp))
        PokemonHeaderTitle(name = name, id = id)
    }
}

@Composable
private fun PokemonImage(
    imageUrl: String,
    modifier: Modifier = Modifier,
) {
    val context = LocalPlatformContext.current
    AsyncImage(
        modifier = modifier,
        model = imageUrl,
        contentDescription = null,
        imageLoader = ImageLoader.Builder(context).build()
    )
}

@Composable
private fun PokemonHeaderTitle(name: String, id: Int) {
    Text(
        text = name,
        fontSize = 32.sp,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold
    )
    Spacer(Modifier.height(4.dp))
    Text(
        text = "#${id}",
        fontSize = 16.sp,
        fontWeight = FontWeight.SemiBold,
    )
}
