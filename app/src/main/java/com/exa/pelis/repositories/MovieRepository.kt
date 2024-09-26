package com.exa.pelis.repositories

import com.exa.pelis.data_source.MovieRemoteDataSource
import com.exa.pelis.model.Movie
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val remoteDataSource: MovieRemoteDataSource
        ) {

    suspend fun getPopularMovies(): List<Movie> {
        // Call to remote data source
        return remoteDataSource.getPopularMovies()
    }

}