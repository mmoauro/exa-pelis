package com.exa.pelis.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingData.Companion.empty
import androidx.paging.cachedIn
import androidx.paging.filter
import com.exa.pelis.data_source.PopularMoviesPagingSource
import com.exa.pelis.data_source.SearchMoviesPagingSource
import com.exa.pelis.model.Movie
import com.exa.pelis.repositories.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _error = MutableStateFlow<String?>(null)
    val error get() = _error.asStateFlow()

    val moviesPager = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = { PopularMoviesPagingSource(repository, onError = ::setError) })
        .flow
        .map {
            // This logic was added because of repeated movies in the list
            val movieMap = mutableSetOf<Int>()
            it.filter { movie ->
                movieMap.add(movie.id)
            }
        }
        .cachedIn(viewModelScope)

    var searchMoviesPager: Flow<PagingData<Movie>> = MutableStateFlow(empty());

    fun setError(error: String?) {
        _error.value = error
    }

    fun searchMovies(query: String) {
        if (query.isEmpty()) {
            searchMoviesPager = MutableStateFlow(empty())
            return
        } else {
            searchMoviesPager = Pager(
                config = PagingConfig(pageSize = 10),
                pagingSourceFactory = {
                    SearchMoviesPagingSource(
                        repository,
                        onError = ::setError,
                        query = query
                    )
                }).flow.cachedIn(viewModelScope)
        }
    }


}