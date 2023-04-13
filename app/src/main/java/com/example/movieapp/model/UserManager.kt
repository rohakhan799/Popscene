package com.example.movieapp.model

object UserManager {
    var bookmarkList: ArrayList<MovieDetails> = ArrayList()
    val bookmarkId: ArrayList<Int> = ArrayList()
    var readGenre: ArrayList<GenreDetails> = ArrayList()
    var userObj: UserDetails? = null
    var allMoviesData: ArrayList<MovieDetails> = ArrayList()
}