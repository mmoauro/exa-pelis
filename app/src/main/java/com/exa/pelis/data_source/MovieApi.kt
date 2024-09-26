package com.exa.pelis.data_source

import com.exa.pelis.model.PopularMoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


interface MovieApi {
    @Headers("Authorization: Bearer ")
    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("page") page: Int): Response<PopularMoviesResponse>
}