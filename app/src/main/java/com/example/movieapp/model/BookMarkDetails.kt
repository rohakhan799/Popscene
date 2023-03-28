package com.example.movieapp.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity(tableName = "BookMarkDetails")
data class BookMarkDetails(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "Id") val bookmarkId: Int,
    @ColumnInfo(name = "userID") var userId: Int,
    @ColumnInfo(name = "movieID") var movieId: Int,
) : Parcelable