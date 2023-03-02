package com.example.movieapp.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "TrendMovies")
data class TrendMovies(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "Id") val trendId: Int,
    @ColumnInfo(name = "MovieId") val movieId: Int
) : Parcelable