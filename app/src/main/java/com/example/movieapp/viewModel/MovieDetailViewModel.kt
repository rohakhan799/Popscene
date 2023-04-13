package com.example.movieapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.model.BookMarkDetails
import com.example.movieapp.model.MovieDetails
import com.example.movieapp.repository.BookmarkRepository
import com.example.movieapp.repository.MoviesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieDetailViewModel @Inject constructor() : ViewModel() {
    var movieTrend: List<MovieDetails>
    var _dataTrend = MutableLiveData<List<MovieDetails>>()
    var trendLiveData: LiveData<List<MovieDetails>> = _dataTrend

    lateinit var movieObj: MovieDetails
    var _movieObj = MutableLiveData<MovieDetails>()
    var movieObjLive: LiveData<MovieDetails> = _movieObj

    var movieBookmark: ArrayList<BookMarkDetails>
    var mutableBookmark = MutableLiveData<List<BookMarkDetails>>()
    var bookmarkLiveData: LiveData<List<BookMarkDetails>> = mutableBookmark

    @Inject
    lateinit var movieRepo: MoviesRepository

    @Inject
    lateinit var bookmarkRepository: BookmarkRepository

    init {
        movieTrend = emptyList()
        movieBookmark = ArrayList()

    }

    suspend fun init() {
        viewModelScope.launch {
            movieRepo.getGenres()
        }
    }

    suspend fun fetchTrendingMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            movieTrend = movieRepo.getTrendMovies()
            _dataTrend.postValue(movieTrend)
        }
    }

    suspend fun getMovie(movieId: Int) {
        movieObj = movieRepo.getMovie(movieId)!!
        _movieObj.postValue(movieObj)
    }

    fun addBookmark(bookMark: BookMarkDetails) {
        viewModelScope.launch(Dispatchers.IO) {
            movieBookmark.add(bookMark)
            bookmarkRepository.addBookmark(bookMark)
            mutableBookmark.postValue(movieBookmark)
        }
    }

    fun removeBookmark(userId: Int, movieId: Int) {
        var bookmarkObj = BookMarkDetails(0, userId, movieId)
        viewModelScope.launch(Dispatchers.IO) {
            movieBookmark.remove(bookmarkObj)
            bookmarkRepository.removeBookmark(userId, movieId)
            mutableBookmark.postValue(bookmarkRepository.getBookmark(userId))
        }
    }

}