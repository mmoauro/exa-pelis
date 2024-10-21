package com.exa.pelis.repositories

import com.exa.pelis.data_source.MovieLocalDataSource
import com.exa.pelis.data_source.MovieRemoteDataSource
import com.exa.pelis.model.MovieDetailsResponse
import com.exa.pelis.model.MovieListApiResponse
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val remoteDataSource: MovieRemoteDataSource,
    private val localDataSource: MovieLocalDataSource
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

    suspend fun saveStarredMovie(movieId: Int) {
        localDataSource.saveStarredMovie(movieId)
    }

    suspend fun removeStarredMovie(movieId: Int) {
        localDataSource.removeStarredMovie(movieId)
    }

    suspend fun getStarredMovies(): Set<Int> {
        return localDataSource.getStarredMovies()
    }

    suspend fun getTrendingMovies(page: Int = 1): MovieListApiResponse {
        return remoteDataSource.getTrendingMovies(page)
    }

}