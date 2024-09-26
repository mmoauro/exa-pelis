package com.exa.pelis.model

import com.google.gson.annotations.SerializedName

class PopularMoviesResponse (
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<Movie>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int,
    val error: String? = null
) {
}