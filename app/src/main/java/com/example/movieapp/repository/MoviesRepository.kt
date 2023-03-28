package com.example.movieapp.repository

import android.util.Log
import com.example.movieapp.data.dao.GenreDao
import com.example.movieapp.data.dao.MoviesDao
import com.example.movieapp.model.MovieDetails
import com.example.movieapp.model.UserManager
import com.example.movieapp.model.UserManager.readGenre
import javax.inject.Inject


class MoviesRepository @Inject constructor(
    private val movieDao: MoviesDao,
    private val genreDao: GenreDao
) {

    suspend fun addMovie(movie: MovieDetails) {
        movieDao.addMovie(movie)
    }

    suspend fun getAllMovies(): List<MovieDetails> {
        return populateGenre(movieDao.getAllMovies())
    }

    suspend fun getTrendMovies(): List<MovieDetails> {
        return populateGenre(movieDao.getTrendMovies())
    }

    suspend fun getRecommendMovies(): List<MovieDetails> {
        return populateGenre(movieDao.getRecommendMovies())
    }

    suspend fun loadGenres() {
        UserManager.readGenre.addAll(genreDao.readGenre())
        for (genre in UserManager.readGenre) {
            genre.genreName.let { UserManager.genreMap.put(genre.genreId, it) }
        }
    }

    fun populateGenre(movies: List<MovieDetails>): List<MovieDetails> {
        movies.forEach { movie ->
            movie.genres.clear()
            movie.genreId.split(",").forEach { genreId ->
                readGenre.firstOrNull { genre ->
                    genre.genreId == genreId.toInt()
                }?.let { genre ->
                    movie.genres.add(genre)
                    Log.d("mytest", "Genres ${movie}}")
                    Log.d("mytest", "Genres ${movie.genres}}")

                }
            }
        }

        return movies
    }
}