package com.exa.pelis.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.exa.pelis.R
import com.exa.pelis.model.Movie
import com.exa.pelis.ui.common.BodyText

@Composable
fun MovieListItem(movie: Movie, onMoviePress: () -> Unit, modifier: Modifier = Modifier) {
    var imageIsLoading by remember { mutableStateOf(true) }
    Column(modifier = modifier.padding(8.dp).clickable { onMoviePress() }) {
        AsyncImage(
            model = "https://image.tmdb.org/t/p/original/${movie.posterPath}",
            //model = R.drawable.broken_image,
            contentDescription = movie.title,
            contentScale = ContentScale.Fit,
            onSuccess = {imageIsLoading = false},
            placeholder = painterResource(R.drawable.loading_image),
            error = painterResource(R.drawable.broken_image),
        )
        BodyText(text = movie.title, fontSize = 20.sp, modifier = Modifier.padding(top = 12.dp))
    }
}

@Composable
@Preview(showBackground = true)
fun PopularMovieItemPreview() {
    MovieListItem(
        Movie(
            id = 1,
            title = "Movie Title fjadsklfjsadl",
            overview = "Movie Overview",
            posterPath = "8cdWjvZQUExUUTzyp4t6EDMubfO.jpg",
        ),
        onMoviePress = {}
    )
}