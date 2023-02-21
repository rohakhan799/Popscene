package com.example.movieapp.database

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize

data class UserDetails(
        val userId: Int,
        var userName: String?,
        var userPass: String?,
) : Parcelable