package com.example.movieapp.model

object UserManager {
    val bookmarkList: ArrayList<MovieDetails> = ArrayList()
    val bookmarkId: ArrayList<Int> = ArrayList()
    val readGenre: ArrayList<GenreDetails> = ArrayList()
    var genreMap: HashMap<Int, String> = HashMap()
    var userObj: UserDetails? = null
}