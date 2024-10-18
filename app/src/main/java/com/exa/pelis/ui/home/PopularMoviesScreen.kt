package com.exa.pelis.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.exa.pelis.R
import com.exa.pelis.model.Movie
import com.exa.pelis.ui.common.BodyText
import com.exa.pelis.ui.common.Button
import com.exa.pelis.ui.common.Loader
import com.exa.pelis.ui.common.Title

@Composable
fun PopularMoviesScreen(viewModel: HomeViewModel, onMoviePress: (Int) -> Unit) {

    val lazyMovies: LazyPagingItems<Movie> = viewModel.moviesPager.collectAsLazyPagingItems()
    val error = viewModel.error.collectAsStateWithLifecycle()

    fun onRetry() {
        viewModel.setError(null)
        lazyMovies.retry()
    }

    Column (Modifier.background(colorResource(id = R.color.background))) {
        Title(text = stringResource(id = R.string.popular_movies), modifier = Modifier.padding(8.dp))
        if (lazyMovies.itemCount == 0 && error.value == null) {
            return Loader(modifier = Modifier.fillMaxHeight())
        }

        if (error.value != null) {
            return Column (modifier = Modifier.padding(8.dp).fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                BodyText(text = error.value!!, color = colorResource(R.color.error))
                Button(text = stringResource(id = R.string.retry), onClick = { onRetry() })
            }
        }
        LazyVerticalGrid(columns = GridCells.Fixed(2)) {

            items(
                count = lazyMovies.itemCount,
                key = lazyMovies.itemKey { movie -> "${movie.id}, ${movie.title}" },
                contentType = lazyMovies.itemContentType{"Movies"}
            ) {
                index ->
                val movie = lazyMovies[index]
                if (movie != null) {
                    PopularMovieItem(movie = movie, onMoviePress = { onMoviePress(movie.id) })
                }

            }
        }
    }
}