package com.exa.pelis.data_source

import com.exa.pelis.model.MovieDetailsResponse
import com.exa.pelis.model.PopularMoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.Locale


interface MovieApi {
    @Headers("Authorization: Bearer ")
    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("page") page: Int, @Query("language") language: String): Response<PopularMoviesResponse>

    @Headers("Authorization: Bearer ")
    @GET("movie/{movieId}")
    suspend fun getMovieDetails(@Path("movieId") movieId: Int, @Query("language") language: String): Response<MovieDetailsResponse>

    @Headers("Authorization: Bearer")
    @GET("movie/{movieId}/similar")
    suspend fun getSimilarMovies(@Path("movieId") movieId: Int, @Query("page") page: Int, @Query("language") language: String): Response<PopularMoviesResponse>
}