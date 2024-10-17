package com.exa.pelis.model

class MovieDetails (
    val movie: Movie,
    val releaseDate: String,
    val runtime: Int,
    val genres: List<Genre>,
    var backdropPath: String?, // I found a movie without backdrop_path
    val voteAverage: Double,
) {
}