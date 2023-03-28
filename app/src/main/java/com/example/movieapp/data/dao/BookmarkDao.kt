package com.example.movieapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movieapp.model.BookMarkDetails

@Dao
interface BookmarkDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addBookmark(bookMarkDetails: BookMarkDetails)

    @Query(value = "DELETE FROM BookMarkDetails WHERE userID =:userId AND movieID =:movieId")
    suspend fun removeBookmark(userId: Int, movieId: Int)

    @Query(value = "SELECT * FROM BookMarkDetails WHERE userID =:userId")
    suspend fun readBookmark(userId: Int): List<BookMarkDetails>

    @Query(value = "SELECT movieID FROM BookMarkDetails WHERE userID =:userId")
    suspend fun readBookmarkID(userId: Int): List<Int>

}