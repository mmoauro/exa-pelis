package com.exa.pelis.ui.movie

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import coil3.compose.AsyncImage
import com.exa.pelis.R
import com.exa.pelis.ui.common.BodyText
import com.exa.pelis.ui.common.Button
import com.exa.pelis.ui.common.Loader
import com.exa.pelis.ui.common.Title
import com.exa.pelis.ui.home.MovieListItem
import java.math.BigDecimal
import java.math.RoundingMode

@Composable
fun MovieDetailsScreen(
    viewModel: MovieDetailViewModel,
    movieId: Int,
    onSimilarMoviePress: (Int) -> Unit
) {

    val details by viewModel.movieDetails.collectAsStateWithLifecycle()
    val isLoading by viewModel.loading.collectAsStateWithLifecycle()
    val error by viewModel.error.collectAsStateWithLifecycle()
    val similarMovies = viewModel.similarMoviesPager.collectAsLazyPagingItems()
    val movieDetails = details

    LaunchedEffect(Unit) {
        viewModel.loadMovieDetails(movieId)
    }

    if (error != null) {
        return Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            BodyText(text = error!!, color = colorResource(R.color.error))
            Button(
                text = stringResource(id = R.string.retry),
                onClick = {
                    viewModel.loadMovieDetails(movieId)
                })
        }
    }
    if (isLoading || movieDetails == null) {
        return Loader(modifier = Modifier.fillMaxSize())
    }

    Column(Modifier.verticalScroll(rememberScrollState())) {
        AsyncImage(
            model = "https://image.tmdb.org/t/p/original/${movieDetails.backdropPath}",
            contentDescription = movieDetails.movie.title,
            placeholder = painterResource(R.drawable.loading_image),
        )

        Column(modifier = Modifier.padding(8.dp)) {
            Title(text = movieDetails.movie.title)
            Spacer(modifier = Modifier.height(4.dp))
            Row {
                AsyncImage(
                    model = R.drawable.star_rate,
                    contentDescription = "Rating",
                    modifier = Modifier
                        .width(24.dp)
                        .height(24.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                BodyText(
                    text = "${
                        BigDecimal(movieDetails.voteAverage).setScale(
                            1,
                            RoundingMode.FLOOR
                        )
                    } / 10"
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            BodyText(text = movieDetails.movie.overview)
            Spacer(modifier = Modifier.height(12.dp))

            MovieAttribute(
                name = stringResource(id = R.string.genres),
                value = movieDetails.genres.joinToString { it.name })
            Spacer(modifier = Modifier.height(8.dp))
            MovieAttribute(
                name = stringResource(id = R.string.year_of_release),
                value = movieDetails.releaseDate
            )
            Spacer(modifier = Modifier.height(8.dp))
            MovieAttribute(
                name = stringResource(id = R.string.duration),
                value = "${movieDetails.runtime} min"
            )
            Spacer(modifier = Modifier.height(8.dp))
            Title(text = stringResource(id = R.string.similar_movies))
            Spacer(modifier = Modifier.height(8.dp))
            LazyRow {
                items(
                    count = similarMovies.itemCount,
                    key = similarMovies.itemKey { movie -> "${movie.id}, ${movie.title}" },
                    contentType = similarMovies.itemContentType{"Movies"}
                ) {
                        index ->
                    val movie = similarMovies[index]
                    if (movie != null) {
                        MovieListItem(movie = movie, onMoviePress = { onSimilarMoviePress(movie.id) }, modifier = Modifier.width(150.dp).height(300.dp).padding(2.dp))
                    }

                }
            }
        }
    }


}

@Composable
private fun MovieAttribute(name: String, value: String) {
    Row {
        BodyText(name)
        Spacer(modifier = Modifier.width(4.dp))
        BodyText(text = value, color = colorResource(id = R.color.on_surface_secondary))
    }
}

