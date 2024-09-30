package com.exa.pelis.ui.home

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.exa.pelis.databinding.HomeActivityBinding
import com.exa.pelis.model.MovieComparator
import com.exa.pelis.ui.movie.MovieDetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeActivity: AppCompatActivity() {

    private lateinit var binding: HomeActivityBinding
    private val viewModel by viewModels<HomeViewModel>()
    private val pagingAdapter = MovieAdapter(::onMovieClicked, MovieComparator)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = HomeActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.popularMoviesList.layoutManager = GridLayoutManager(this, 2)
        binding.popularMoviesList.adapter = pagingAdapter

        pagingAdapter.addLoadStateListener { loadState ->
            if (loadState.hasError) {
                val errorState = when {
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                    else -> null
                }
                Toast.makeText(this, errorState?.error?.message, Toast.LENGTH_SHORT).show()
            }
        }

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
        lifecycleScope.launch {
            viewModel.moviesPager.collectLatest { pagingData ->
                pagingAdapter.submitData(pagingData)
            }
        }
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