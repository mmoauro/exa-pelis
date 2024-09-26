package com.exa.pelis.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    private val _movies = MutableStateFlow<List<Movie>>(emptyList())
    val movies get() = _movies.asStateFlow()

    private val _loading = MutableStateFlow(true)
    val loading  get() = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error get() = _error.asStateFlow()

    init {
        getPopularMovies()
    }

    private fun getPopularMovies() {
        viewModelScope.launch {
            _loading.value = true
            val popularMovies = repository.getPopularMovies()
            _movies.value = popularMovies.results
            _error.value = popularMovies.error
            _loading.value = false
        }

    }
}