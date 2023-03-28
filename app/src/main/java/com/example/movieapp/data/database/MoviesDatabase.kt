package com.example.movieapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.movieapp.data.dao.BookmarkDao
import com.example.movieapp.data.dao.GenreDao
import com.example.movieapp.data.dao.MoviesDao
import com.example.movieapp.data.dao.UserDao
import com.example.movieapp.model.*

@Database(
    entities = [MovieDetails::class, GenreDetails::class, TrendMovies::class, RecommendMovies::class, UserDetails::class, BookMarkDetails::class],
    version = 1,
    exportSchema = false
)
abstract class MoviesDatabase : RoomDatabase() {
    abstract fun movieDao(): MoviesDao
    abstract fun genreDao(): GenreDao
    abstract fun UserDao(): UserDao
    abstract fun BookmarkDao(): BookmarkDao

    companion object {

    }
}