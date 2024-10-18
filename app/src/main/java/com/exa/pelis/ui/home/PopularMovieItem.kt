package com.exa.pelis.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.exa.pelis.model.Movie
import com.exa.pelis.ui.common.BodyText
import com.exa.pelis.ui.common.Loader

@Composable
fun PopularMovieItem(movie: Movie, onMoviePress: () -> Unit) {
    var imageIsLoading by remember { mutableStateOf(true) }
    Column(modifier = Modifier.padding(8.dp).defaultMinSize(minHeight = 250.dp).clickable { onMoviePress() }) {
        AsyncImage(
            model = "https://image.tmdb.org/t/p/original/${movie.posterPath}",
            //model = R.drawable.broken_image,
            contentDescription = movie.title,
            contentScale = ContentScale.Fit,
            onSuccess = {imageIsLoading = false},
        )
        if (imageIsLoading) {
            Column(modifier =Modifier.fillMaxHeight(), verticalArrangement = Arrangement.Center) {
                Loader()
            }
        }
        BodyText(text = movie.title, fontSize = 20.sp, modifier = Modifier.padding(top = 12.dp))
    }
}

@Composable
@Preview(showBackground = true)
fun PopularMovieItemPreview() {
    PopularMovieItem(
        Movie(
            id = 1,
            title = "Movie Title fjadsklfjsadl",
            overview = "Movie Overview",
            posterPath = "8cdWjvZQUExUUTzyp4t6EDMubfO.jpg",
        ),
        onMoviePress = {}
    )
}