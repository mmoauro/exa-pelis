package com.exa.pelis.data_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.exa.pelis.model.Movie
import com.exa.pelis.repositories.MovieRepository

class TrendingMoviesPagingSource(
    private val movieRepository: MovieRepository,
    private val onError: (String?) -> Unit,
): PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val page = params.key ?: 1
        return try {
            val response = movieRepository.getTrendingMovies(page)
            if (response.error != null) {
                onError(response.error)
                return LoadResult.Error(Exception(response.error))
            }
            LoadResult.Page(
                data = response.results,
                prevKey = null,
                nextKey = if (response.results.isEmpty()) null else page + 1,
            )
        } catch (e: Exception) {
            onError(e.message)
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}