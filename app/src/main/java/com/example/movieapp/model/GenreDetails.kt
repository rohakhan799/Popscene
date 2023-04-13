package com.example.movieapp.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "GenreDetails")
data class GenreDetails(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "Id")
    @SerializedName(value = "id")
    val genreId: Int,
    @ColumnInfo(name = "Name")
    @SerializedName(value = "name")
    var genreName: String,
) : Parcelable