package com.example.movieapp.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "GenreDetails")
data class GenreDetails(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "Id") val genreId: Int,
    @ColumnInfo(name = "Name") var genreName: String,
) : Parcelable