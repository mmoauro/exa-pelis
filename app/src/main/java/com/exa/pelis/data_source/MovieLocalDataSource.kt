package com.exa.pelis.data_source

import android.content.SharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject


class MovieLocalDataSource @Inject constructor(private val sharedPreferences: SharedPreferences) {


    /*
    * Save a movie as starred
    * returns if the action was completed successfully
     */
    suspend fun saveStarredMovie(movieId: Int): Boolean {
        return withContext(Dispatchers.IO) {
            val current = sharedPreferences.getStringSet("starred", mutableSetOf())
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            val set = mutableSetOf(movieId.toString())
            set.addAll(current!!)
            try {
                editor.putStringSet("starred", set)
                editor.apply()
                true
            } catch (e: IOException) {
                e.printStackTrace()
                false
            }
        }
    }

    suspend fun removeStarredMovie(movieId: Int): Boolean {
        return withContext(Dispatchers.IO) {
            val current = sharedPreferences.getStringSet("starred", mutableSetOf())
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            val set: MutableSet<String> = current!!.toMutableSet()
            set.remove(movieId.toString())
            try {
                editor.putStringSet("starred", set)
                editor.apply()

                true
            } catch (e: IOException) {
                e.printStackTrace()
                false
            }
        }
    }

    suspend fun getStarredMovies(): Set<Int> {
        return withContext(Dispatchers.IO) {
            sharedPreferences.getStringSet("starred", mutableSetOf())!!.map { it.toInt() }.toSet()
        }

    }
}