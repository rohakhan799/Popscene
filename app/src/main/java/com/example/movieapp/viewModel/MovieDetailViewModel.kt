package com.example.movieapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.model.MovieDetails
import com.example.movieapp.repository.MoviesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieDetailViewModel @Inject constructor() : ViewModel() {
    var movieTrend: List<MovieDetails>
    var mutable_dataTrend = MutableLiveData<List<MovieDetails>>()
    var trendLiveData: LiveData<List<MovieDetails>> = mutable_dataTrend

    @Inject
    lateinit var movieRepo: MoviesRepository

    init {
        movieTrend = emptyList()
    }

    suspend fun init() {
        viewModelScope.launch {
            movieRepo.loadGenres()
        }
    }

    suspend fun fetchTrendingMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            movieTrend = movieRepo.getTrendMovies()
            mutable_dataTrend.postValue(movieTrend)
        }
    }
}