package com.exa.pelis.data_source

import com.exa.pelis.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieRemoteDataSource @Inject constructor() {

    suspend fun getPopularMovies(): List<Movie> {
        return withContext(Dispatchers.IO) {
            listOf(Movie("Terminator", 1, "A cyborg is sent from the future to kill Sarah Connor."),
                Movie("The Matrix", 2, "A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers."),
                Movie("The Lord of the Rings: The Fellowship of the Ring", 3, "A meek Hobbit from the Shire and eight companions set out on a journey to destroy the powerful One Ring and save Middle-earth from the Dark Lord Sauron."),
                Movie("The Shawshank Redemption", 4, "Two"))
        }
    }

}