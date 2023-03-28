package com.example.movieapp.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import kotlinx.parcelize.Parceler

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
    @Ignore val genres: ArrayList<GenreDetails> = ArrayList()

    companion object : Parceler<MovieDetails> {
        override fun MovieDetails.write(parcel: Parcel, flags: Int) {
            parcel.writeInt(movieId)
            parcel.writeString(movieTitle)
            parcel.writeInt(movieRating)
            parcel.writeLong(movieDuration)
            parcel.writeString(genreId)
            parcel.writeString(movieDesc)
            parcel.writeString(coverImageUrl)
            parcel.writeList(genres)
        }

        override fun create(parcel: Parcel): MovieDetails {
            return MovieDetails(
                parcel.readInt(),
                parcel.readString() ?: "",
                parcel.readInt(),
                parcel.readLong(),
                parcel.readString() ?: "",
                parcel.readString() ?: "",
                parcel.readString() ?: "",
            ).apply {
                parcel.readList(genres, GenreDetails::class.java.classLoader)
            }
        }

    }
}