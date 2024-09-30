package com.exa.pelis.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.exa.pelis.databinding.HomeActivityBinding
import com.exa.pelis.ui.movie.MovieDetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class HomeActivity: AppCompatActivity() {

    private lateinit var binding: HomeActivityBinding
    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i("HomeActivity", "onCreate")
        super.onCreate(savedInstanceState)

        binding = HomeActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.popularMoviesList.layoutManager = GridLayoutManager(this, 2)

        handleMoviesChange()
        handleLoadingChange()
        handleError()
    }

    private fun handleLoadingChange() {
        viewModel.loading.onEach { loading -> {
            // Show or hide loading
        } }
    }

    private fun handleMoviesChange() {
        viewModel.movies.onEach { movies ->
            binding.popularMoviesList.adapter = MovieAdapter(movies, ::onMovieClicked)
        }.launchIn(lifecycleScope)
    }

    private fun handleError() {
        viewModel.error.onEach { error ->
            if (error != null) {
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
                // TODO create custom toast layout
            }
        }.launchIn(lifecycleScope)
    }

    private fun onMovieClicked(movieId: Int) {
        val intent = Intent(this, MovieDetailActivity::class.java)
        intent.putExtra("movieId", movieId)
        startActivity(intent)
    }
}