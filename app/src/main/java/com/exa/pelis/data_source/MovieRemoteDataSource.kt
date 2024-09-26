package com.exa.pelis.data_source

import com.exa.pelis.model.PopularMoviesResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.UnknownHostException
import javax.inject.Inject

class MovieRemoteDataSource @Inject constructor(
    private val movieApi: MovieApi
) {

    suspend fun getPopularMovies(page: Int): PopularMoviesResponse {
        return withContext(Dispatchers.IO) {
            try {
                val response = movieApi.getPopularMovies(page)
                response.body() ?: PopularMoviesResponse(0, emptyList(), 0, 0)
            }
            catch (e: UnknownHostException) {
                PopularMoviesResponse(0, emptyList(), 0, 0, "No internet connection")
            }
            catch (e: Exception) {
                PopularMoviesResponse(0, emptyList(), 0, 0, "An unexpected error occurred")
            }
        }
    }

}