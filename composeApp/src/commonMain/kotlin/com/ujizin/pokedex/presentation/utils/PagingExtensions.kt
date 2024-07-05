package com.ujizin.pokedex.presentation.utils

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import app.cash.paging.compose.LazyPagingItems

// Below is a workaround. More info: https://issuetracker.google.com/issues/177245496.
// Based on: https://stackoverflow.com/a/75908331/11903815
@Composable
fun <T : Any> LazyPagingItems<T>.rememberLazyListState(): LazyListState {
    return when (itemCount) {
        // Return a different LazyListState instance.
        0 -> remember(this) { LazyListState(0, 0) }
        // Return rememberLazyListState (normal case).
        else -> androidx.compose.foundation.lazy.rememberLazyListState()
    }
}
