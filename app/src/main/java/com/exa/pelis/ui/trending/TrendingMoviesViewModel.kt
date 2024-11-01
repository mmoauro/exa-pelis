package com.exa.pelis.ui.trending

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.filter
import com.exa.pelis.data_source.TrendingMoviesPagingSource
import com.exa.pelis.repositories.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class TrendingMoviesViewModel @Inject constructor(
    private val repository: MovieRepository,
): ViewModel() {

    private val _error = MutableStateFlow<String?>(null)
    val error get() = _error.asStateFlow()

    val trendingMoviesPager = Pager(config = PagingConfig(pageSize = 20), pagingSourceFactory = { TrendingMoviesPagingSource(repository, ::setError ) })
        .flow
        .map {
            val movieMap = mutableSetOf<Int>()
            it.filter { movie ->
                movieMap.add(movie.id)
            }
        }
        .cachedIn(viewModelScope)

    fun setError(message: String?) {
        _error.value = message
    }

}