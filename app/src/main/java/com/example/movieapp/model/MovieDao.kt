package com.example.movieapp.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao

interface MovieData{
    @Insert(onConflict = 5)
    suspend fun addMovie(movie:MovieDetails)

    @Query("SELECT * FROM movieDetailsTable order by movieId ASC ")
    suspend fun readAllData():LiveData<List<MovieDetails>>
}