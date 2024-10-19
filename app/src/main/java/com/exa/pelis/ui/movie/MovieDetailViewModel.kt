package com.exa.pelis.ui.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.exa.pelis.data_source.PopularMoviesPagingSource
import com.exa.pelis.data_source.SimilarMoviesPagingSource
import com.exa.pelis.model.Movie
import com.exa.pelis.model.MovieDetails
import com.exa.pelis.model.MovieDetailsResponse
import com.exa.pelis.repositories.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val repository: MovieRepository,
): ViewModel() {


    private val _movieDetails = MutableStateFlow<MovieDetails?>(null)
    val movieDetails get() = _movieDetails.asStateFlow()

    private val _loading = MutableStateFlow(true)
    val loading get() = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error get() = _error.asStateFlow()

    var similarMoviesPager: Flow<PagingData<Movie>> = MutableStateFlow(PagingData.empty())


    fun loadMovieDetails(movieId: Int) {
        similarMoviesPager = Pager(config = PagingConfig(pageSize = 20), pagingSourceFactory = { SimilarMoviesPagingSource(repository, ::setError, movieId ) })
            .flow
            .cachedIn(viewModelScope)
        viewModelScope.launch {
            _movieDetails.value = null
            _loading.value = true
            _error.value = null
            val response: MovieDetailsResponse = repository.getMovieDetails(movieId)
            _movieDetails.value = response.toMovieDetails()
            _error.value = response.error
            _loading.value = false
        }
    }

    private fun setError(error: String?) {
        _error.value = error
    }



}