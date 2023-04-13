package com.example.movieapp.repository

import com.example.movieapp.data.dao.BookmarkDao
import com.example.movieapp.model.BookMarkDetails
import com.example.movieapp.model.UserManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BookmarkRepository @Inject constructor(
    private val bookmarkDao: BookmarkDao
) {
    val readBookmark: ArrayList<BookMarkDetails> = ArrayList()

     fun getBookmark(userId: Int): List<BookMarkDetails> {
        return bookmarkDao.readBookmark(userId)
    }

    suspend fun addBookmark(bookMark: BookMarkDetails) {
        bookmarkDao.addBookmark(bookMark)
    }

    fun removeBookmark(userId: Int, movieId: Int) {
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
    }
}