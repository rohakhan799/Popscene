package com.example.movieapp.model

import com.google.gson.annotations.SerializedName

class MovieResponse {
    @SerializedName("results")
    var movies: ArrayList<MovieDetails> = ArrayList()

    @SerializedName("genres")
    var genres: ArrayList<GenreDetails> = ArrayList()
}