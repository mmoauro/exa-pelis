package com.exa.pelis.data_source

import com.exa.pelis.model.MovieDetailsResponse
import com.exa.pelis.model.MovieListApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query


interface MovieApi {
    @Headers("Authorization: ")
    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("page") page: Int, @Query("language") language: String): Response<MovieListApiResponse>

    @Headers("Authorization: ")
    @GET("movie/{movieId}")
    suspend fun getMovieDetails(@Path("movieId") movieId: Int, @Query("language") language: String): Response<MovieDetailsResponse>

    @Headers("Authorization: ")
    @GET("movie/{movieId}/similar")
    suspend fun getSimilarMovies(@Path("movieId") movieId: Int, @Query("page") page: Int, @Query("language") language: String): Response<MovieListApiResponse>

    @Headers("Authorization: ")
    @GET("search/movie?include_adult=true")
    suspend fun searchMovies(@Query("query") query: String, @Query("page") page: Int, @Query("language") language: String): Response<MovieListApiResponse>

    @Headers("Authorization: ")
    @GET("trending/movie/week")
    suspend fun getTrendingMovies(@Query("page") page: Int, @Query("language") language: String): Response<MovieListApiResponse>


}