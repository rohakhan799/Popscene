package com.example.movieapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.model.BookMarkDetails
import com.example.movieapp.model.MovieDetails
import com.example.movieapp.model.UserManager
import com.example.movieapp.repository.BookmarkRepository
import com.example.movieapp.repository.MoviesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


class MoviesViewModel @Inject constructor() : ViewModel() {
    var allMovies: List<MovieDetails>
    private var _allMoviesLiveData = MutableLiveData<List<MovieDetails>>()
    var allMoviesLiveData: LiveData<List<MovieDetails>> = _allMoviesLiveData

    var movieTrend: List<MovieDetails>
    var _dataTrend = MutableLiveData<List<MovieDetails>>()
    var trendLiveData: LiveData<List<MovieDetails>> = _dataTrend

    var movieRecommend: List<MovieDetails>
    var _dataRecommend = MutableLiveData<List<MovieDetails>>()
    var recommendLiveData: LiveData<List<MovieDetails>> = _dataRecommend

    var movieBookmark: ArrayList<BookMarkDetails>
    var mutableBookmark = MutableLiveData<List<BookMarkDetails>>()
    var bookmarkLiveData: LiveData<List<BookMarkDetails>> = mutableBookmark

    var allBookmarkMovies: List<MovieDetails>
    private var _allBookmarkMovies = MutableLiveData<List<MovieDetails>>()
    var allBookmarkMoviesLiveData: LiveData<List<MovieDetails>> = _allBookmarkMovies

    @Inject
    lateinit var movieRepo: MoviesRepository

    @Inject
    lateinit var bookmarkRepository: BookmarkRepository

    init {
        allMovies = emptyList()
        allBookmarkMovies = emptyList()
        movieBookmark = ArrayList()
        movieRecommend = emptyList()
        movieTrend = emptyList()
    }

    suspend fun init() {
        viewModelScope.launch {
            movieRepo.getGenres()
            //movieRepo.updateBookmarkList()
            bookmarkRepository.loadBookmarks()
        }
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
            movieBookmark.remove(bookmarkObj)
            bookmarkRepository.removeBookmark(userId, movieId)
            mutableBookmark.postValue(bookmarkRepository.getBookmark(userId))
    }

    fun fetchAllBookmarkMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            allBookmarkMovies = UserManager.bookmarkList
            _allBookmarkMovies.postValue(allBookmarkMovies)
        }
    }

    suspend fun fetchAllMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            allMovies = movieRepo.getAllMovies()
            _allMoviesLiveData.postValue(allMovies)
        }
    }

    suspend fun fetchRecommendedMovies(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            movieRecommend = movieRepo.getRecommendMovies(movieId)
            _dataRecommend.postValue(movieRecommend)
        }
    }

    suspend fun fetchTrendingMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            movieTrend = movieRepo.getTrendMovies()
            _dataTrend.postValue(movieTrend)
        }
    }
}