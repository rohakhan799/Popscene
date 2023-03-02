package com.example.movieapp.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "MovieDetails")
data class MovieDetails(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "Id") val movieId: Int,
    @ColumnInfo(name = "Title") var movieTitle: String,
    @ColumnInfo(name = "Rating") var movieRating: Int,
    @ColumnInfo(name = "Duration") var movieDuration: Long,
    @ColumnInfo(name = "Genre") var genreId: String,
    @ColumnInfo(name = "Description") var movieDesc: String,
    @ColumnInfo(name = "CoverImageUrl") val coverImageUrl: String,
) : Parcelable {
    @Ignore
    val genres: ArrayList<GenreDetails> = ArrayList()
}