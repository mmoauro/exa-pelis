package com.exa.pelis.model

class MovieDetails (
    val movie: Movie,
    val releaseDate: String,
    val runtime: Int,
    val genres: List<Genre>,
    val backdropPath: String
) {
}