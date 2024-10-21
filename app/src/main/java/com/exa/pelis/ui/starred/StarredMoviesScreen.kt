package com.exa.pelis.ui.starred

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.exa.pelis.R
import com.exa.pelis.ui.common.Title
import com.exa.pelis.ui.home.MovieListItem

@Composable
fun StarredMoviesScreen(viewModel: StarredMoviesViewModel, onMoviePress: (Int) -> Unit) {
    val starredMovies by viewModel.starredMovieDetails.collectAsStateWithLifecycle()

    Column {
        Box (contentAlignment = Alignment.Center, modifier = Modifier.height(56.dp)) {
            Title(text = stringResource(id = R.string.starred), modifier = Modifier.padding(8.dp))
        }
        LazyVerticalGrid(columns = GridCells.Fixed(2)) {
            items(items = starredMovies) { movie ->
                MovieListItem(movie = movie, onMoviePress = { onMoviePress(movie.id) })
            }


        }
    }

}