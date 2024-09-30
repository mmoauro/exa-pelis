package com.exa.pelis.data_source

import com.exa.pelis.model.MovieDetails
import com.exa.pelis.model.MovieDetailsResponse
import com.exa.pelis.model.PopularMoviesResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.UnknownHostException
import java.util.Locale
import javax.inject.Inject

class MovieRemoteDataSource @Inject constructor(
    private val movieApi: MovieApi
) {

    suspend fun getPopularMovies(page: Int): PopularMoviesResponse {
        return withContext(Dispatchers.IO) {
            try {
                val response = movieApi.getPopularMovies(page, Locale.getDefault().toString())
                response.body() ?: PopularMoviesResponse(0, emptyList(), 0, 0)
            } catch (e: UnknownHostException) {
                PopularMoviesResponse(0, emptyList(), 0, 0, "No internet connection") // TODO: Add string resource
            } catch (e: Exception) {
                PopularMoviesResponse(0, emptyList(), 0, 0, "An unexpected error occurred") // same as above
            }
        }
    }

    suspend fun getMovieDetails(movieId: Int): MovieDetailsResponse {
        return withContext(Dispatchers.IO) {
            try {
                val response = movieApi.getMovieDetails(movieId, Locale.getDefault().toString())
                response.body() ?: MovieDetailsResponse("", 0, "", "", "", 0, emptyList(), "","No internet connection") // same as above
            } catch (e: Exception) {
                MovieDetailsResponse("", 0, "", "", "", 0, emptyList(), "","An unexpected error occurred") // same as above
            }
        }
    }

}