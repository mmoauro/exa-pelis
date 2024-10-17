package com.exa.pelis.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.exa.pelis.R
import com.exa.pelis.ui.common.Loader
import com.exa.pelis.ui.common.Title

@Composable
fun PopularMoviesScreen(viewModel: HomeViewModel, onMoviePress: (Int) -> Unit) {

    val movies by viewModel.movies.collectAsStateWithLifecycle()

    if (movies.isEmpty()) {
        return Loader()
    }

    Column {
        Title(text = stringResource(id = R.string.popular_movies), modifier = Modifier.padding(8.dp))

        LazyVerticalGrid(columns = GridCells.Fixed(2)) {
            items(movies) { movie ->
                PopularMovieItem(movie = movie, onMoviePress = { onMoviePress(movie.id) })
            }
        }
    }


}