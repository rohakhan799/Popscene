package com.example.movieapp.database

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
@Parcelize
//@Entity(tableName = "movieDetailsTable")
data class MovieDetails(
   // @PrimaryKey(autoGenerate = true)
    val movieId: Int,
    var movieTitle: String?,
    var movieRating: String?,
    var movieTime: String?,
    var movieGenre: String?,
    var movieDesc: String?,
    var movieCast: String?,
    val coverImageUrl: String?,

/*    var voteCount: Int,
    var moviePlot: String,
    val downloadMov: Boolean,
    val playMovie: Boolean*/
) : Parcelable