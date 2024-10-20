package com.exa.pelis.data_source

import android.content.Context
import com.exa.pelis.R
import com.exa.pelis.model.MovieDetailsResponse
import com.exa.pelis.model.MovieListApiResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.UnknownHostException
import java.util.Locale
import javax.inject.Inject

class MovieRemoteDataSource @Inject constructor(
    private val movieApi: MovieApi,
    @ApplicationContext
    private val context: Context
) {

    suspend fun getPopularMovies(page: Int): MovieListApiResponse {
        return withContext(Dispatchers.IO) {
            try {
                val response = movieApi.getPopularMovies(page, Locale.getDefault().toString())
                response.body() ?: MovieListApiResponse(0, emptyList(), 0, 0)
            } catch (e: UnknownHostException) {
                MovieListApiResponse(
                    0,
                    emptyList(),
                    0,
                    0,
                    context.resources.getString(R.string.no_internet_connection)
                )
            } catch (e: Exception) {
                MovieListApiResponse(
                    0,
                    emptyList(),
                    0,
                    0,
                    context.resources.getString(R.string.unexpected_error)
                )
            }
        }
    }

    suspend fun getMovieDetails(movieId: Int): MovieDetailsResponse {
        return withContext(Dispatchers.IO) {
            try {
                val response = movieApi.getMovieDetails(movieId, Locale.getDefault().toString())
                response.body() ?: MovieDetailsResponse(
                    "",
                    0,
                    "",
                    "",
                    "",
                    0,
                    emptyList(),
                    "",
                    0.0,
                    context.resources.getString(R.string.no_internet_connection)
                )
            } catch (e: UnknownHostException) {
                MovieDetailsResponse(
                    "",
                    0,
                    "",
                    "",
                    "",
                    0,
                    emptyList(),
                    "",
                    0.0,
                    context.resources.getString(R.string.no_internet_connection)
                )
            } catch (e: Exception) {
                MovieDetailsResponse(
                    "",
                    0,
                    "",
                    "",
                    "",
                    0,
                    emptyList(),
                    "",
                    0.0,
                    context.resources.getString(R.string.unexpected_error)
                )
            }
        }
    }

    suspend fun getSimilarMovies(movieId: Int, page: Int = 1): MovieListApiResponse {
        return withContext(Dispatchers.IO) {
            try {
                val response =
                    movieApi.getSimilarMovies(movieId, page, Locale.getDefault().toString())
                response.body() ?: MovieListApiResponse(0, emptyList(), 0, 0)
            } catch (e: UnknownHostException) {
                MovieListApiResponse(
                    0,
                    emptyList(),
                    0,
                    0,
                    context.resources.getString(R.string.no_internet_connection)
                ) // same as above
            } catch (e: Exception) {
                MovieListApiResponse(
                    0,
                    emptyList(),
                    0,
                    0,
                    context.resources.getString(R.string.unexpected_error)
                )
            }
        }
    }

    suspend fun searchMovies(query: String, page: Int = 1): MovieListApiResponse {
        return withContext(Dispatchers.IO) {
            try {
                val response = movieApi.searchMovies(query, page, Locale.getDefault().toString())
                response.body() ?: MovieListApiResponse(0, emptyList(), 0, 0)
            } catch (e: UnknownHostException) {
                MovieListApiResponse(
                    0,
                    emptyList(),
                    0,
                    0,
                    context.resources.getString(R.string.no_internet_connection)
                )
            } catch (e: Exception) {
                MovieListApiResponse(
                    0,
                    emptyList(),
                    0,
                    0,
                    context.resources.getString(R.string.unexpected_error)
                )
            }
        }
    }

}