package com.exa.pelis.ui.movie

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.exa.pelis.databinding.MovieDetailsActivityBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MovieDetailActivity: AppCompatActivity() {
    private lateinit var binding: MovieDetailsActivityBinding
    private val viewModel: MovieDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MovieDetailsActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val movieId = intent.getIntExtra("movieId", 0)
        viewModel.loadMovieDetails(movieId)
        handleDetailsChange()
    }

    private fun handleDetailsChange() {
        viewModel.movieDetails.onEach { movieDetails ->
            if (movieDetails == null) {
                return@onEach
            }
            binding.movieName.text = movieDetails.movie.title
            binding.yearOfRelease.text = movieDetails.releaseDate
            binding.genresList.text = movieDetails.genres.joinToString { it.name }
            binding.overview.text = movieDetails.movie.overview
            binding.movieDetails.visibility = View.VISIBLE
            binding.duration.text = "${movieDetails.runtime} min"

            Glide.with(binding.root)
                .load("https://image.tmdb.org/t/p/w500${movieDetails.backdropPath}")
                .listener(object: RequestListener<Drawable> {
                    override fun onResourceReady(
                        resource: Drawable,
                        model: Any,
                        target: Target<Drawable>?,
                        dataSource: DataSource,
                        isFirstResource: Boolean
                    ): Boolean {
                        binding.loader.visibility = View.INVISIBLE
                        return false
                    }

                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>,
                        isFirstResource: Boolean
                    ): Boolean {
                        TODO("Not yet implemented")
                    }
                })
                .into(binding.movieImage)


        }.launchIn(lifecycleScope)
    }
}