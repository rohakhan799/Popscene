package com.example.movieapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movieapp.model.GenreDetails


@Dao
interface GenreDao {
    @Insert(onConflict=OnConflictStrategy.IGNORE)
    suspend fun addGenre(genre: GenreDetails)

    @Query(value = "SELECT * FROM GenreDetails ORDER BY Id ASC ")
    fun readGenre():List<GenreDetails>
}