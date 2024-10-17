package com.exa.pelis.ui.movie

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.exa.pelis.ui.common.Loader

@Composable
fun MovieScreen(
    viewModel: MovieDetailViewModel
) {
    val details by viewModel.movieDetails.collectAsStateWithLifecycle()

    if (details == null) {
        return Loader()
    }

    //MovieDetailsScreen(details!!)

}