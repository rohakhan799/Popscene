package com.example.movieapp.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "UserDetails")

data class UserDetails(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "Id") val userId: Int,
    @ColumnInfo(name = "Email") var userEmail: String,
    @ColumnInfo(name = "UserName") var userName: String?,
    @ColumnInfo(name = "Password") var userPass: String?,
) : Parcelable