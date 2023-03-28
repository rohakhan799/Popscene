package com.example.movieapp.repository

import android.util.Log
import com.example.movieapp.data.dao.BookmarkDao
import com.example.movieapp.data.dao.MoviesDao
import com.example.movieapp.model.BookMarkDetails
import com.example.movieapp.model.MovieDetails
import com.example.movieapp.model.UserManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BookmarkRepository @Inject constructor(
    private val bookmarkDao: BookmarkDao,
    private val moviesDao: MoviesDao
) {
    val readBookmark: ArrayList<BookMarkDetails> = ArrayList()

    suspend fun getBookmark(userId:Int): List<BookMarkDetails> {
        return bookmarkDao.readBookmark(userId)
    }

    suspend fun addBookmark(bookMark: BookMarkDetails) {
        bookmarkDao.addBookmark(bookMark)
    }

    suspend fun removeBookmark(userId: Int, movieId: Int) {
        bookmarkDao.removeBookmark(userId, movieId)
    }

    suspend fun readBookmarkID(userId: Int) {
        if (UserManager.bookmarkId.isEmpty()) {
            UserManager.bookmarkId.addAll(bookmarkDao.readBookmarkID(userId))
        }
    }

    suspend fun loadBookmarks() {
        withContext(Dispatchers.IO) {
            if (readBookmark.isEmpty()) {
                UserManager.userObj?.let { bookmarkDao.readBookmark(it.userId) }
                    ?.let { readBookmark.addAll(it) }
            }
        }
        Log.d("mytest","loadBookmarks ${readBookmark}")
    }

    suspend fun getAllBookmarkMovies(): List<MovieDetails> {
        return withContext(Dispatchers.IO) {
            populateBookmark(moviesDao.getAllMovies())
        }
    }

    fun populateBookmark(movies: List<MovieDetails>): List<MovieDetails> {
        var bookmarkMovies: ArrayList<MovieDetails> = ArrayList()
        for (bookmark in readBookmark) {
            UserManager.bookmarkId.add(bookmark.movieId)
        }
        movies.forEach { movie ->
            if (UserManager.bookmarkId.contains(movie.movieId)) {
                bookmarkMovies.add(movie)
                UserManager.bookmarkList.add(movie)
            }
        }
        return bookmarkMovies
    }
}