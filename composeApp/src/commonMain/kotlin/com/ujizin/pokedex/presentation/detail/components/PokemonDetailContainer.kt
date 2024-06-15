package com.ujizin.pokedex.presentation.detail.components

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
import com.ujizin.pokedex.presentation.utils.color
import com.ujizin.pokedex.presentation.utils.toColor

@Composable
fun PokemonDetailContainer(
    modifier: Modifier = Modifier,
    pokemon: Pokemon,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        PokemonHeader(
            id = pokemon.id,
            name = pokemon.capitalizedName,
            imageUrl = pokemon.imageUrl,
        )
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
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp,
        )
        Spacer(Modifier.height(24.dp))
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            stats.forEach { stat ->
                PokemonStat(stat)
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

@Composable
private fun PokemonHeader(
    id: Int,
    name: String,
    imageUrl: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PokemonImage(imageUrl)
        Spacer(Modifier.height(8.dp))
        PokemonHeaderTitle(name = name, id = id)
    }
}

@Composable
private fun PokemonImage(imageUrl: String) {
    val context = LocalPlatformContext.current
    AsyncImage(
        modifier = Modifier.size(180.dp),
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
