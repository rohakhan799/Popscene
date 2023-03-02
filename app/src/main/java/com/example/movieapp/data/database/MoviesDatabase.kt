package com.example.movieapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.movieapp.data.dao.GenreDao
import com.example.movieapp.data.dao.MoviesDao
import com.example.movieapp.model.GenreDetails
import com.example.movieapp.model.MovieDetails
import com.example.movieapp.model.RecommendMovies
import com.example.movieapp.model.TrendMovies

@Database(entities = [MovieDetails::class,GenreDetails::class,TrendMovies::class,RecommendMovies::class], version = 1, exportSchema = false)
abstract class MoviesDatabase : RoomDatabase() {
    abstract fun movieDao(): MoviesDao
    abstract fun genreDao(): GenreDao

    companion object {
        private var INSTANCE: MoviesDatabase? = null
        fun getDatabase(context: Context): MoviesDatabase {
            val temp_instance = INSTANCE
            if (temp_instance != null) {
                return temp_instance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MoviesDatabase::class.java,
                    name = "movies_database"
                ).createFromAsset("database/Movies.db")
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}