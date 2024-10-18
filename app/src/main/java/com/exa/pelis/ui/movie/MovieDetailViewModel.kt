package com.exa.pelis.ui.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exa.pelis.model.MovieDetails
import com.exa.pelis.model.MovieDetailsResponse
import com.exa.pelis.repositories.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
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

    fun loadMovieDetails(movieId: Int) {
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



}