package com.exa.pelis.ui.trending

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.exa.pelis.R
import com.exa.pelis.model.Movie
import com.exa.pelis.ui.common.Loader
import com.exa.pelis.ui.common.Title
import com.exa.pelis.ui.home.MovieListItem

@Composable
fun TrendingMoviesScreen(viewModel: TrendingMoviesViewModel, onMoviePress: (Int) -> Unit) {
    val lazyMovies: LazyPagingItems<Movie> =
        viewModel.trendingMoviesPager.collectAsLazyPagingItems()


    Column {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.height(56.dp)) {
            Title(
                text = stringResource(id = R.string.trending_movies),
                modifier = Modifier.padding(8.dp)
            )
        }
        if (lazyMovies.loadState.refresh is LoadState.Loading) {
            return Loader(modifier = Modifier.fillMaxHeight())
        }

        LazyVerticalGrid(columns = GridCells.Fixed(2)) {
            items(
                count = lazyMovies.itemCount,
                key = lazyMovies.itemKey { movie -> "${movie.id}, ${movie.title}" }, // TODO: there are repeated keys
                contentType = lazyMovies.itemContentType { "Movies" }
            ) { index ->
                val movie = lazyMovies[index]
                if (movie != null) {
                    MovieListItem(movie = movie, onMoviePress = { onMoviePress(movie.id) })
                }

            }
        }
    }

}