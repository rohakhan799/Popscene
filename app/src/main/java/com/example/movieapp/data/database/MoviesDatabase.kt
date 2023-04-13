package com.example.movieapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.movieapp.data.dao.*
import com.example.movieapp.model.*

@Database(
    entities = [MovieDetails::class, GenreDetails::class, TrendMovies::class, RecommendMovies::class, UserDetails::class, BookMarkDetails::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(GenreConverter::class)

abstract class MoviesDatabase : RoomDatabase() {
    abstract fun movieDao(): MoviesDao
    abstract fun genreDao(): GenreDao
    abstract fun UserDao(): UserDao
    abstract fun BookmarkDao(): BookmarkDao

    companion object {
    }
}