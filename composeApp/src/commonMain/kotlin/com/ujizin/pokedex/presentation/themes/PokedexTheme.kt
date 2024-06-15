package com.ujizin.pokedex.presentation.themes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun PokedexTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = MaterialTheme.colors.copy(
            primary = Color(0xFFBD3736),
            secondary = Color(0xCCEE1515),
            background = Color(0xFFF3EFEF)
        ),
        content = {
            Box(
                modifier = Modifier.background(MaterialTheme.colors.background),
                content = { content() },
            )
        }
    )
}
