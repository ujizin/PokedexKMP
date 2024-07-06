package com.ujizin.pokedex.presentation.themes

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun PokedexTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = MaterialTheme.colors.copy(
            primary = Red700,
            secondary = Red800,
            background = Gray500,
        ),
        content = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.background)
                    .windowInsetsPadding(WindowInsets.safeDrawing),
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
