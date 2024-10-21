package com.exa.pelis.ui.home

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.exa.pelis.R
import com.exa.pelis.ui.common.BodyText
import com.exa.pelis.ui.movie.MovieDetailViewModel
import com.exa.pelis.ui.movie.MovieDetailsScreen
import com.exa.pelis.ui.starred.StarredMoviesScreen
import com.exa.pelis.ui.starred.StarredMoviesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private val viewModel by viewModels<HomeViewModel>()
    private val movieDetailsViewModel by viewModels<MovieDetailViewModel>()
    private val starredMoviesViewModel by viewModels<StarredMoviesViewModel>()

    // Define the routes
    @Serializable
    object Popular

    @Serializable
    data class MovieDetails(val movieId: Int)

    @Serializable
    object Starred

    @Serializable
    object Trending

    sealed class Screen(
        val route: Any,
        @StringRes val resourceId: Int,
        @DrawableRes val iconId: Int
    ) {
        object PopularMovies : Screen(Popular, R.string.bottom_navigation_popular, R.drawable.fire)
        object Starred : Screen(
            HomeActivity.Starred,
            R.string.bottom_navigation_starred,
            R.drawable.unstarred_movie
        )

        object Trending :
            Screen(HomeActivity.Trending, R.string.bottom_navigation_trending, R.drawable.trending)
    }

    private val items = listOf(
        Screen.PopularMovies,
        Screen.Trending,
        Screen.Starred
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            val navigationController = rememberNavController()

            fun navigateToMovieDetails(movieId: Int) {
                navigationController.navigate(route = MovieDetails(movieId))
            }
            Scaffold(
                bottomBar = {
                    BottomNavigation(backgroundColor = colorResource(id = R.color.on_primary)) {
                        val navBackStackEntry by navigationController.currentBackStackEntryAsState()
                        val currentDestination = navBackStackEntry?.destination
                        items.forEach { screen ->
                            BottomNavigationItem(
                                icon = {
                                    Image(
                                        painter = painterResource(id = screen.iconId),
                                        contentDescription = stringResource(screen.resourceId),
                                    )
                                },
                                label = { BodyText(stringResource(screen.resourceId)) },
                                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                                onClick = {
                                    navigationController.navigate(screen.route) {
                                        // Pop up to the start destination of the graph to
                                        // avoid building up a large stack of destinations
                                        // on the back stack as users select items
                                        popUpTo(navigationController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        // Avoid multiple copies of the same destination when
                                        // reselecting the same item
                                        launchSingleTop = true
                                        // Restore state when reselecting a previously selected item
                                        restoreState = true
                                    }
                                }
                            )
                        }
                    }
                }
            ) { padding ->
                NavHost(
                    navController = navigationController,
                    startDestination = Popular,
                    modifier = Modifier
                        .background(color = colorResource(id = R.color.surface))
                        .fillMaxSize()
                        .padding(padding)
                ) {
                    composable<Popular> {
                        PopularMoviesScreen(viewModel, onMoviePress = ::navigateToMovieDetails)
                    }
                    composable<MovieDetails> { backStackEntry ->
                        val details: MovieDetails = backStackEntry.toRoute()
                        MovieDetailsScreen(
                            movieDetailsViewModel,
                            starredMoviesViewModel,
                            details.movieId,
                            onSimilarMoviePress = ::navigateToMovieDetails
                        )
                    }
                    composable<Starred> {
                        StarredMoviesScreen(
                            starredMoviesViewModel,
                            onMoviePress = ::navigateToMovieDetails
                        )
                    }

                }
            }

        }
    }
}