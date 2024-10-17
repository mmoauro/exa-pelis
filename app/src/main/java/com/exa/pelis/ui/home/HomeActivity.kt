package com.exa.pelis.ui.home

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.exa.pelis.databinding.HomeActivityBinding
import com.exa.pelis.ui.movie.MovieDetailViewModel
import com.exa.pelis.ui.movie.MovieDetailsScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable

@AndroidEntryPoint
class HomeActivity: AppCompatActivity() {

    private lateinit var binding: HomeActivityBinding
    private val viewModel by viewModels<HomeViewModel>()
    private val movieDetailsViewModel by viewModels<MovieDetailViewModel>()
    //private val pagingAdapter = MovieAdapter(::onMovieClicked, MovieComparator)

    @Serializable
    object Popular
    @Serializable
    data class MovieDetails(val movieId: Int)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navigationController = rememberNavController()
            NavHost(navController = navigationController, startDestination = Popular) {
                composable<Popular> {
                    PopularMoviesScreen(viewModel, onMoviePress = {movieId ->
                        navigationController.navigate(route = MovieDetails(movieId))
                    })
                }
                composable<MovieDetails> { backStackEntry ->
                    val details: MovieDetails = backStackEntry.toRoute()
                    MovieDetailsScreen(movieDetailsViewModel, details.movieId)
                }

            }
        }


        //handleMoviesChange()
        //handleLoadingChange()
        //handleError()
    }

    /*

    private fun handleLoadingChange() {
        viewModel.loading.onEach { loading -> {
            // Show or hide loading
        } }
    }

    private fun handleMoviesChange() {
        lifecycleScope.launch {
            viewModel.moviesPager.collectLatest { pagingData ->
                pagingAdapter.submitData(pagingData)
            }
        }
    }

    private fun handleError() {
        viewModel.error.onEach { error ->
            if (error != null) {
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
                // TODO create custom toast layout
            }
        }.launchIn(lifecycleScope)
    }

    private fun onMovieClicked(movieId: Int) {
        val intent = Intent(this, MovieDetailActivity::class.java)
        intent.putExtra("movieId", movieId)
        startActivity(intent)
    }

     */
}