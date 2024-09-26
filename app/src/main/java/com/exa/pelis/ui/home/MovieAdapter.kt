package com.exa.pelis.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.exa.pelis.databinding.MovieListItemBinding
import com.exa.pelis.model.Movie

class MovieAdapter(private val movies: List<Movie>) :
    RecyclerView.Adapter<MovieAdapter.MovieHolder>() {

    class MovieHolder(private val binding: MovieListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind (movie: Movie) {
            binding.movieName.text = movie.title
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MovieHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = MovieListItemBinding.inflate(inflater, viewGroup, false)
        return MovieHolder(binding)
    }

    override fun onBindViewHolder(movieHolder: MovieHolder, position: Int) {
        val movie = movies[position]
        movieHolder.bind(movie)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

}