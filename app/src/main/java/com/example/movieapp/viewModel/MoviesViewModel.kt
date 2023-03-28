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
    var mutableDataTrend = MutableLiveData<List<MovieDetails>>()
    var trendLiveData: LiveData<List<MovieDetails>> = mutableDataTrend

    var movieRecommend: List<MovieDetails>
    var mutableDataRecommend = MutableLiveData<List<MovieDetails>>()
    var recommendLiveData: LiveData<List<MovieDetails>> = mutableDataRecommend

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
            movieRepo.loadGenres()
            bookmarkRepository.loadBookmarks()
        }
    }

    fun addBookmark(bookMark: BookMarkDetails) {
        viewModelScope.launch(Dispatchers.IO) {
            movieBookmark.add(bookMark)
            bookmarkRepository.addBookmark(bookMark)
            //mutableBookmark.postValue(UserManager.userObj?.let { bookmarkRepository.getBookmark(it.userId) })
            mutableBookmark.postValue(movieBookmark)
        }
    }

    fun removeBookmark(userId: Int, movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            //movieBookmark.add(bookMark)
            bookmarkRepository.removeBookmark(userId, movieId)
            mutableBookmark.postValue(UserManager.userObj?.let { bookmarkRepository.getBookmark(it.userId) })
        }
    }

    suspend fun fetchAllBookmarkMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            allBookmarkMovies = bookmarkRepository.getAllBookmarkMovies()
            _allBookmarkMovies.postValue(allBookmarkMovies)
        }
    }

    suspend fun fetchAllMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            allMovies = movieRepo.getAllMovies()
            _allMoviesLiveData.postValue(allMovies)
            /*movieBookmark = UserManager.userObj?.let { bookmarkRepository.getBookmark(it.userId) }!!
            mutableBookmark.postValue(movieBookmark)*/
        }
    }

    suspend fun fetchRecommendedMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            movieRecommend = movieRepo.getRecommendMovies()
            mutableDataRecommend.postValue(movieRecommend)
        }
    }

    suspend fun fetchTrendingMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            movieTrend = movieRepo.getTrendMovies()
            mutableDataTrend.postValue(movieTrend)
        }
    }
}