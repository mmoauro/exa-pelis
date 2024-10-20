package com.exa.pelis.repositories

import com.exa.pelis.data_source.MovieRemoteDataSource
import com.exa.pelis.model.MovieDetailsResponse
import com.exa.pelis.model.MovieListApiResponse
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val remoteDataSource: MovieRemoteDataSource,
) {

    suspend fun getPopularMovies(page: Int = 1): MovieListApiResponse {
        return remoteDataSource.getPopularMovies(page)
    }

    suspend fun getMovieDetails(movieId: Int): MovieDetailsResponse {
        return remoteDataSource.getMovieDetails(movieId)
    }

    suspend fun getSimilarMovies(movieId: Int, page: Int = 1): MovieListApiResponse {
        return remoteDataSource.getSimilarMovies(movieId, page)
    }

    suspend fun searchMovies(query: String, page: Int = 1): MovieListApiResponse {
        return remoteDataSource.searchMovies(query, page)
    }

}