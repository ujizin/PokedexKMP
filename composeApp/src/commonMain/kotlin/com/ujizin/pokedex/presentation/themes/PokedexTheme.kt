package com.ujizin.pokedex.presentation.themes

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.core.MutableTransitionState
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

@Composable
fun PokedexPreviewTheme(content: @Composable AnimatedContentScope.() -> Unit) {
    AnimatedContent(MutableTransitionState(true)) {
        PokedexTheme {
            content()
        }
    }
}
