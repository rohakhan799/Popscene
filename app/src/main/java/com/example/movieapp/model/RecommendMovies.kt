package com.example.movieapp.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.parcelize.Parceler

@Parcelize
@Entity(tableName = "RecommendMovies")
data class RecommendMovies(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "Id")
    @SerializedName(value = "id")
    override val movieId: Int,

    @ColumnInfo(name = "Title")
    @SerializedName(value = "title")
    override var movieTitle: String,

    @ColumnInfo(name = "Rating")
    @SerializedName(value = "vote_average")
    override var movieRating: Double,

    @ColumnInfo(name = "Description")
    @SerializedName(value = "overview")
    override var movieDesc: String,

    @ColumnInfo(name = "CoverImageUrl")
    @SerializedName(value = "poster_path")
    override val coverImageUrl: String,

    @ColumnInfo(name = "Genre")
    @SerializedName(value = "genre_ids")
    override var genreId: ArrayList<String> = ArrayList()

) : MovieDetails(movieId, movieTitle, movieRating, movieDesc, coverImageUrl, genreId), Parcelable {
    @Ignore
    override val genres: ArrayList<GenreDetails> = ArrayList()

    companion object : Parceler<MovieDetails> {
        override fun MovieDetails.write(parcel: Parcel, flags: Int) {
            parcel.writeInt(movieId)
            parcel.writeString(movieTitle)
            parcel.writeDouble(movieRating)
            parcel.writeString(movieDesc)
            parcel.writeString(coverImageUrl)
            parcel.writeList(genreId)
            parcel.writeList(genres)
        }

        override fun create(parcel: Parcel): MovieDetails {
            return MovieDetails(
                parcel.readInt(),
                parcel.readString() ?: "",
                parcel.readDouble(),
                parcel.readString() ?: "",
                parcel.readString() ?: ""
            ).apply {
                parcel.readList(genreId, GenreDetails::class.java.classLoader)
                parcel.readList(genres, GenreDetails::class.java.classLoader)
            }
        }
    }
}