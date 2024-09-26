package com.exa.pelis.repositories

import com.exa.pelis.data_source.MovieRemoteDataSource
import com.exa.pelis.model.PopularMoviesResponse
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val remoteDataSource: MovieRemoteDataSource
        ) {

    suspend fun getPopularMovies(page: Int = 1): PopularMoviesResponse {
        return remoteDataSource.getPopularMovies(page)
    }

}