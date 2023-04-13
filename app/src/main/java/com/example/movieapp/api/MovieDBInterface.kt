package com.example.movieapp.api

import com.example.movieapp.model.MovieDetails
import com.example.movieapp.model.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

//https://api.themoviedb.org/3/movie/550?api_key=f5adf09b5b302fb8235f10c0bff05832

interface MovieDBInterface {

    @GET(value = "movie/{movie_id}")
    suspend fun getMovieDetails(@Path(value = "movie_id") id: Int): Response<MovieDetails>

    @GET(value = "movie/popular")
    suspend fun getAllMovies(): Response<MovieResponse>

    @GET(value = "movie/{movie_id}/recommendations")
    suspend fun getRecommendMovies(@Path(value = "movie_id") id: Int): Response<MovieResponse>

    @GET(value = "movie/top_rated")
    suspend fun getTrendMovies(): Response<MovieResponse>

    @GET(value = "genre/movie/list")
    suspend fun getGenreList(): Response<MovieResponse>
}