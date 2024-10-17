package com.exa.pelis.ui.movie

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.exa.pelis.R
import com.exa.pelis.model.Movie
import com.exa.pelis.model.MovieDetails
import com.exa.pelis.ui.common.BodyText
import com.exa.pelis.ui.common.Loader
import com.exa.pelis.ui.common.Title
import java.math.BigDecimal
import java.math.RoundingMode

@Composable
fun MovieDetailsScreen(
    viewModel: MovieDetailViewModel,
    movieId: Int
) {

    LaunchedEffect(Unit){
        viewModel.loadMovieDetails(movieId)
    }

    var imageIsLoading by remember { mutableStateOf(true) }
    val details by viewModel.movieDetails.collectAsStateWithLifecycle()
    val movieDetails = details

    if (movieDetails == null) {
        return Loader()
    }

    Column (Modifier.background(color = colorResource(id = R.color.background))) {

        if (imageIsLoading) {
            Loader()
        }
        AsyncImage(
            model = "https://image.tmdb.org/t/p/original/${movieDetails.backdropPath}",
            //model = R.drawable.broken_image,
            contentDescription = movieDetails.movie.title,
            contentScale = ContentScale.Fit,
            onSuccess = {imageIsLoading = false},
        )

        Column(modifier = Modifier.padding(8.dp)) {
            Title(text = movieDetails.movie.title)
            Spacer(modifier = Modifier.height(4.dp))
            Row {
                AsyncImage(model = R.drawable.star_rate, contentDescription = "Rating", modifier = Modifier.width(24.dp).height(24.dp))
                Spacer(modifier = Modifier.width(4.dp))
                BodyText(text = "${BigDecimal(movieDetails.voteAverage).setScale(1, RoundingMode.FLOOR)} / 10")
            }
            Spacer(modifier = Modifier.height(8.dp))
            BodyText(text = movieDetails.movie.overview)
            Spacer(modifier = Modifier.height(12.dp))

            MovieAttribute(name = stringResource(id = R.string.genres), value = movieDetails.genres.joinToString { it.name })
            Spacer(modifier = Modifier.height(8.dp))
            MovieAttribute(name = stringResource(id = R.string.year_of_release), value = movieDetails.releaseDate)
            Spacer(modifier = Modifier.height(8.dp))
            MovieAttribute(name = stringResource(id = R.string.duration), value = "${movieDetails.runtime} min")
            Spacer(modifier = Modifier.height(8.dp))
        }
    }


}

@Composable
private fun MovieAttribute(name: String, value: String) {
    Row  {
        BodyText(name)
        Spacer(modifier = Modifier.width(4.dp))
        BodyText(text = value, color = colorResource(id = R.color.on_surface_secondary))
    }
}

