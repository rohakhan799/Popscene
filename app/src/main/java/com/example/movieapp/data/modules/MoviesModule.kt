package com.example.movieapp.data.modules

import android.app.Application
import androidx.room.Room
import com.example.movieapp.data.dao.BookmarkDao
import com.example.movieapp.data.dao.GenreDao
import com.example.movieapp.data.dao.MoviesDao
import com.example.movieapp.data.dao.UserDao
import com.example.movieapp.data.database.MoviesDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MoviesModule{
    @Provides
    @Singleton
    fun getMoviesDatabase(application: Application): MoviesDatabase {
        return Room.databaseBuilder(
            application.applicationContext,
            MoviesDatabase::class.java,
            name = "movies_database"
        ).createFromAsset("database/Movies.db")
            .build()
    }

    @Provides
    @Singleton
    fun getMoviesDao(application: Application): MoviesDao {
        return getMoviesDatabase(application).movieDao()
    }

    @Provides
    @Singleton
    fun getGenreDao(application: Application): GenreDao {
        return getMoviesDatabase(application).genreDao()
    }

    @Provides
    @Singleton
    fun getUserDao(application: Application): UserDao {
        return getMoviesDatabase(application).UserDao()
    }

    @Provides
    @Singleton
    fun getBookmarkDao(application: Application): BookmarkDao {
        return getMoviesDatabase(application).BookmarkDao()
    }
}