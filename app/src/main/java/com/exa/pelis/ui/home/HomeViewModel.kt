package com.exa.pelis.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.exa.pelis.data_source.PopularMoviesPagingSource
import com.exa.pelis.repositories.MovieRepository
import com.exa.pelis.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor (
    private val repository: MovieRepository
        ): ViewModel() {

    private val _error = MutableStateFlow<String?>(null)
    val error get() = _error.asStateFlow()

    val moviesPager = Pager(config = PagingConfig(pageSize = 20), pagingSourceFactory = {PopularMoviesPagingSource(repository, onError = ::setError )})
    .flow
        .cachedIn(viewModelScope)

    fun setError(error: String?) {
        _error.value = error
    }


}