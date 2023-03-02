package com.example.movieapp.viewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.database.MoviesDatabase
import com.example.movieapp.model.MovieDetails
import com.example.movieapp.repository.MoviesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MoviesViewModel : ViewModel() {
    var allMovies: List<MovieDetails>
    private var _allMoviesLiveData = MutableLiveData<List<MovieDetails>>()
    var allMoviesLiveData: LiveData<List<MovieDetails>> = _allMoviesLiveData

    var movieTrend: List<MovieDetails>
    var mutable_dataTrend = MutableLiveData<List<MovieDetails>>()
    var trendLiveData: LiveData<List<MovieDetails>> = mutable_dataTrend

    var movieRecommend: List<MovieDetails>
    var mutable_dataRecommend = MutableLiveData<List<MovieDetails>>()
    var recommendLiveData: LiveData<List<MovieDetails>> = mutable_dataRecommend

    lateinit var movieRepo: MoviesRepository

    init {
        allMovies = emptyList()
        movieRecommend = emptyList()
        movieTrend = emptyList()
    }

    fun init(application: Application) {
        val moviesDao = MoviesDatabase.getDatabase(application).movieDao()
        val genreDao = MoviesDatabase.getDatabase(application).genreDao()
        movieRepo = MoviesRepository(moviesDao,genreDao)
    }

    fun fetchAllMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            allMovies = movieRepo.getAllMovies()
            _allMoviesLiveData.postValue(allMovies)
        }
    }

    fun fetchRecommendedMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            movieRecommend = movieRepo.getRecommendMovies()
            mutable_dataRecommend.postValue(movieRecommend)
        }
    }

    fun fetchTrendingMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            movieTrend = movieRepo.getTrendMovies()
            mutable_dataTrend.postValue(movieTrend)
        }
    }

    fun getAllWords(): LiveData<List<MovieDetails>> {
        return allMoviesLiveData
    }
}