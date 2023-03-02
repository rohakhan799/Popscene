package com.example.movieapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.movieapp.model.MovieDetails

@Dao
interface MoviesDao {

    @Insert(onConflict=5)
    suspend fun addMovie(movie: MovieDetails)

    @Query(value = "SELECT * FROM MovieDetails ORDER BY Id ASC ")
    fun getAllMovies():List<MovieDetails>

    @Transaction
    @Query("SELECT * FROM MovieDetails m1, TrendMovies t1 WHERE m1.Id = t1.MovieId")
    fun getTrendMovies():List<MovieDetails>

    @Transaction
    @Query("SELECT * FROM MovieDetails m1, RecommendMovies t1 WHERE m1.Id = t1.MovieId")
    fun getRecommendMovies():List<MovieDetails>

}