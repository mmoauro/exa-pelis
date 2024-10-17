package com.exa.pelis.model

import com.google.gson.annotations.SerializedName

class MovieDetailsResponse (
    @SerializedName("title")
    val title: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("runtime")
    val runtime: Int,
    @SerializedName("genres")
    val genres: List<Genre>,
    @SerializedName("backdrop_path")
    val backdropPath: String,
    @SerializedName("vote_average")
    val voteAverage: Double,
    val error: String? = null
) {

    fun toMovieDetails(): MovieDetails {
        return MovieDetails(
            movie = Movie(
                title = title,
                id = id,
                overview = overview,
                posterPath = posterPath
            ),
            releaseDate = releaseDate,
            runtime = runtime,
            genres = genres,
            backdropPath = backdropPath,
            voteAverage = voteAverage
        )
    }
}