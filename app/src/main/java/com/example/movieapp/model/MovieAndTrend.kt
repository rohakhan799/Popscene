/*
package com.example.movieapp.model

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "MovieAndTrend")
data class MovieAndTrend(
    @Embedded val movieId: MovieDetails,
    @Relation(
        parentColumn = "Id",
        entityColumn = "MovieId"
    )
    val trendID: TrendMovies
) : Parcelable
*/
