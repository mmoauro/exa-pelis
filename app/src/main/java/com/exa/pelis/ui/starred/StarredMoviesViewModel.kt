package com.exa.pelis.ui.starred

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exa.pelis.model.Movie
import com.exa.pelis.repositories.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StarredMoviesViewModel @Inject constructor(
    private val repository: MovieRepository,
) : ViewModel() {

    private val _starredMovies = MutableStateFlow<Set<Int>>(setOf())
    val starredMovies get() = _starredMovies.asStateFlow()

    private val _starredMovieDetails = MutableStateFlow<List<Movie>>(listOf())
    val starredMovieDetails get() = _starredMovieDetails.asStateFlow()

    private val _loadingDetails = MutableStateFlow(true)
    val loadingDetails get() = _loadingDetails.asStateFlow()

    private var _error = MutableStateFlow<String?>(null)
    val error get() = _error.asStateFlow()


    init {
        viewModelScope.launch {
            _starredMovies.value = repository.getStarredMovies()
            obtainMovieDetails()
        }
    }

    fun saveStarredMovie(movieId: Int) {
        viewModelScope.launch {
            repository.saveStarredMovie(movieId)
            _starredMovies.value = repository.getStarredMovies()
            _starredMovieDetails.value = mutableListOf(
                repository.getMovieDetails(movieId).toMovieDetails().movie
            ) + _starredMovieDetails.value
        }
    }

    fun removeStarredMovie(movieId: Int) {
        viewModelScope.launch {
            repository.removeStarredMovie(movieId)
            _starredMovies.value = repository.getStarredMovies()
            _starredMovieDetails.value = _starredMovieDetails.value.filter { it.id != movieId }
        }
    }

    fun isStarred(movieId: Int): Boolean {
        return starredMovies.value.contains(movieId)
    }

    fun obtainMovieDetails() {
        viewModelScope.launch {
            _error.value = null
            _loadingDetails.value = true
            val movies = mutableListOf<Movie>()
            val ids = starredMovies.value
            // It would be better to obtain the details all in only one call, but the API does not support it
            ids.forEach {
                val movieDetails = repository.getMovieDetails(it)
                _error.value = movieDetails.error
                movies.add(movieDetails.toMovieDetails().movie)
            }
            _starredMovieDetails.value = movies
            _loadingDetails.value = false
        }

    }

}