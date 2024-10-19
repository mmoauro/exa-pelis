package com.exa.pelis.ui.home

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.exa.pelis.R
import com.exa.pelis.ui.movie.MovieDetailViewModel
import com.exa.pelis.ui.movie.MovieDetailsScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable

@AndroidEntryPoint
class HomeActivity: AppCompatActivity() {
    private val viewModel by viewModels<HomeViewModel>()
    private val movieDetailsViewModel by viewModels<MovieDetailViewModel>()

    // Define the routes
    @Serializable
    object Popular
    @Serializable
    data class MovieDetails(val movieId: Int)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            val navigationController = rememberNavController()

            fun navigateToMovieDetails(movieId: Int) {
                navigationController.navigate(route = MovieDetails(movieId))
            }
            NavHost(navController = navigationController, startDestination = Popular, modifier = Modifier.background(color = colorResource(id = R.color.background)).fillMaxSize()) {
                composable<Popular> {
                    PopularMoviesScreen(viewModel, onMoviePress = ::navigateToMovieDetails)
                }
                composable<MovieDetails> { backStackEntry ->
                    val details: MovieDetails = backStackEntry.toRoute()
                    MovieDetailsScreen(movieDetailsViewModel, details.movieId, onSimilarMoviePress = ::navigateToMovieDetails )
                }

            }
        }
}
}