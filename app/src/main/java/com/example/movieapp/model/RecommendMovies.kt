package com.example.movieapp.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(
    tableName = "RecommendMovies"
)
data class RecommendMovies(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "Id") val recommendId: Int,
    @ColumnInfo(name = "MovieId") val movieId: Int
) : Parcelable