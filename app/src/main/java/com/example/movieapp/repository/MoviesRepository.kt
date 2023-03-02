package com.example.movieapp.repository

import com.example.movieapp.data.dao.GenreDao
import com.example.movieapp.data.dao.MoviesDao
import com.example.movieapp.model.GenreDetails
import com.example.movieapp.model.MovieDetails
import com.example.movieapp.model.UserManager


class MoviesRepository(private val movieDao: MoviesDao, private val genreDao: GenreDao) {

    suspend fun addMovie(movie: MovieDetails) {
        movieDao.addMovie(movie)
    }

    val readGenre: ArrayList<GenreDetails> = ArrayList()

    fun getAllMovies(): List<MovieDetails> {
        return populateGenre(movieDao.getAllMovies())
    }

    fun getTrendMovies(): List<MovieDetails> {
        return populateGenre(movieDao.getTrendMovies())
    }

    fun getRecommendMovies(): List<MovieDetails> {
        return populateGenre(movieDao.getRecommendMovies())
    }

    fun populateGenre(movies: List<MovieDetails>): List<MovieDetails> {
        if (readGenre.isEmpty()) {
            loadGenres()
        }
        for (genre in readGenre) {
            genre.genreName.let { UserManager.genreMap.put(genre.genreId, it) }
        }
        movies.forEach { movie ->
            movie.genres.clear()
            movie.genreId.split(",").forEach { genreId ->
                movie.genres.add(
                    readGenre.first { genre ->
                        genre.genreId == genreId.toInt()
                    }
                )
            }
        }
        return movies
    }

    private fun loadGenres() {
        readGenre.clear()
        readGenre.addAll(genreDao.readGenre())
    }
}