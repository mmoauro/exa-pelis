package com.exa.pelis.ui.home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
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
import kotlinx.coroutines.delay

@Composable
fun PopularMoviesScreen(viewModel: HomeViewModel, onMoviePress: (Int) -> Unit) {

    val lazyMovies: LazyPagingItems<Movie> = viewModel.moviesPager.collectAsLazyPagingItems()
    val lazySearchMovies: LazyPagingItems<Movie> =
        viewModel.searchMoviesPager.collectAsLazyPagingItems()
    val error by viewModel.error.collectAsStateWithLifecycle()
    val isLoading = lazyMovies.loadState.refresh is LoadState.Loading

    var searchInputIsVisible by rememberSaveable { mutableStateOf(false) }
    var searchInput by rememberSaveable() { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }

    fun onRetry() {
        viewModel.setError(null)
        lazyMovies.retry()
    }

    LaunchedEffect(key1 = searchInput) {
        delay(1500)
        viewModel.searchMovies(searchInput)
    }



    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .height(56.dp)
        ) {

            if (searchInputIsVisible) {
                val focusManager = LocalFocusManager.current
                OutlinedTextField(
                    value = searchInput,
                    onValueChange = { searchInput = it },
                    label = { BodyText(text = stringResource(id = R.string.search)) },
                    modifier = Modifier
                        .fillMaxSize()
                        .focusRequester(focusRequester),
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = colorResource(id = R.color.on_surface_text),
                        unfocusedTextColor = colorResource(id = R.color.on_surface_text),
                        cursorColor = colorResource(id = R.color.on_surface_secondary),
                        focusedContainerColor = colorResource(id = R.color.background),
                        unfocusedContainerColor = colorResource(id = R.color.background),
                        focusedIndicatorColor = colorResource(id = R.color.primary),
                        focusedLabelColor = colorResource(id = R.color.primary),
                    ),
                    trailingIcon = {
                        Image(
                            painter = painterResource(id = R.drawable.clear),
                            contentDescription = stringResource(id = R.string.close),
                            modifier = Modifier
                                .clickable {
                                    searchInputIsVisible = false
                                    focusManager.clearFocus()
                                }

                        )
                    },
                )

            } else {
                Title(text = stringResource(id = R.string.popular_movies))
                Image(
                    painter = painterResource(id = R.drawable.search),
                    contentDescription = stringResource(id = R.string.search),
                    modifier = Modifier
                        .width(36.dp)
                        .height(36.dp)
                        .clickable {
                            searchInputIsVisible = true
                            //focusRequester.requestFocus()
                        }
                )

            }
        }



        if (isLoading) {
            return Loader(modifier = Modifier.fillMaxHeight())
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
                Button(text = stringResource(id = R.string.retry), onClick = { onRetry() })
            }
        }

        if (!searchInputIsVisible) {
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
        } else {
            LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                items(
                    count = lazySearchMovies.itemCount,
                    key = lazySearchMovies.itemKey { movie -> "${movie.id}" },
                    contentType = lazySearchMovies.itemContentType { "Movies" }
                ) { index ->
                    val movie = lazySearchMovies[index]
                    if (movie != null) {
                        MovieListItem(movie = movie, onMoviePress = { onMoviePress(movie.id) })
                    }

                }
            }
        }
    }
}