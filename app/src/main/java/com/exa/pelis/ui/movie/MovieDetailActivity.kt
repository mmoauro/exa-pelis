package com.exa.pelis.ui.movie

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailActivity: AppCompatActivity() {
    private val viewModel: MovieDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadMovieDetails(intent.getIntExtra("movieId", 1))
        setContent {
            //MovieScreen(viewModel)
        }

    }

}