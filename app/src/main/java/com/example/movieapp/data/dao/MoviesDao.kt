package com.example.movieapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.movieapp.model.MovieDetails
import com.example.movieapp.model.RecommendMovies
import com.example.movieapp.model.TrendMovies

@Dao
interface MoviesDao {

    @Insert(onConflict = 5)
    suspend fun addMovie(movieList: List<MovieDetails>)

    @Insert(onConflict = 5)
    suspend fun addMovieRecommend(movieList: List<RecommendMovies>)

    @Insert(onConflict = 5)
    suspend fun addMovieTrend(movieList: List<TrendMovies>)

    @Query(value = "SELECT * FROM TrendMovies t1, RecommendMovies r1 WHERE t1.Id != r1.Id ")
    suspend fun getAllMoviesTrend(): List<MovieDetails>

    @Query(value = "SELECT * FROM MovieDetails ORDER BY Id ASC ")
    suspend fun getAllMovies(): List<MovieDetails>

    @Query(value = "SELECT * FROM RecommendMovies ")
    suspend fun getAllMoviesRecommend(): List<MovieDetails>
}

