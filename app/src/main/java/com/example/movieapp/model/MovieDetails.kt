package com.example.movieapp.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.*
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import kotlinx.android.parcel.Parcelize
import kotlinx.parcelize.Parceler


@Parcelize
@Entity
open class MovieDetails(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "Id")
    @SerializedName(value = "id")
    open val movieId: Int,

    @ColumnInfo(name = "Title")
    @SerializedName(value = "title")
    open var movieTitle: String,

    @ColumnInfo(name = "Rating")
    @SerializedName(value = "vote_average")
    open var movieRating: Double,

    @ColumnInfo(name = "Description")
    @SerializedName(value = "overview")
    open var movieDesc: String,

    @ColumnInfo(name = "CoverImageUrl")
    @SerializedName(value = "poster_path")
    open val coverImageUrl: String,

    @ColumnInfo(name = "Genre")
    @SerializedName(value = "genre_ids")
    open var genreId: ArrayList<String> = ArrayList()

) : Parcelable {
    @Ignore
    open val genres: ArrayList<GenreDetails> = ArrayList()

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

class GenreConverter {
    @TypeConverter
    fun fromString(value: String?): ArrayList<String> {
        val listType = object : TypeToken<ArrayList<String>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: ArrayList<String?>): String {
        return Gson().toJson(list)
    }
}