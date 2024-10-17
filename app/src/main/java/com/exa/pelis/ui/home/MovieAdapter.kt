package com.exa.pelis.ui.home

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.exa.pelis.R
import com.exa.pelis.model.Movie

/*
class MovieAdapter(private val onMovieClicked: (Int) -> Unit, diffCallback: DiffUtil.ItemCallback<Movie>) :
    PagingDataAdapter<Movie, MovieAdapter.MovieHolder>(diffCallback) {


    inner class MovieHolder(private val binding: Any) : RecyclerView.ViewHolder(binding.root) {

        fun bind (movie: Movie?) {
            if (movie == null) {
                return
            }
            //binding.movieName.text = movie.title
            //binding.imageLoader.visibility = View.VISIBLE
            // Load movie poster image


            // Add click listener
            //binding.root.setOnClickListener {
              //  onMovieClicked(movie.id)
            //}
        }
    }

    /*
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MovieHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = MovieListItemBinding.inflate(inflater, viewGroup, false)
        return MovieHolder(binding)
    }

     */

    override fun onBindViewHolder(movieHolder: MovieHolder, position: Int) {
        val movie = getItem(position)
        movieHolder.bind(movie)
    }


}

 */