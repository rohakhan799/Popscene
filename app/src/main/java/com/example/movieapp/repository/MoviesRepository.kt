package com.example.movieapp.repository

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.movieapp.api.MovieDBClient
import com.example.movieapp.application.MyApplication
import com.example.movieapp.data.dao.GenreDao
import com.example.movieapp.data.dao.MoviesDao
import com.example.movieapp.data.database.MoviesDatabase
import com.example.movieapp.model.*
import com.example.movieapp.model.UserManager.readGenre
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class MoviesRepository @Inject constructor(
    private val context: MyApplication,
    private val movieDao: MoviesDao,
    private val genreDao: GenreDao,
    private val moviesDatabase: MoviesDatabase
) {
    var bookmarks: ArrayList<MovieDetails> = ArrayList()
    suspend fun addMovie(movieList: List<MovieDetails>) {
        movieDao.addMovie(movieList)
    }

    suspend fun getAllMoviesDb(): List<MovieDetails> {
        return movieDao.getAllMovies()
    }

    suspend fun getTrendMoviesDb(): List<MovieDetails> {
        return movieDao.getAllMoviesTrend()
    }

    suspend fun getRecommendMoviesDb(): List<MovieDetails> {
        return movieDao.getAllMoviesRecommend()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    suspend fun getAllMovies(): List<MovieDetails> {
        var movieDetails: List<MovieDetails>
        if (isInternetAvailable(context.applicationContext)) {
            movieDetails = getAllMoviesAPI()
        } else {
            movieDetails = getAllMoviesDb()
        }
        UserManager.allMoviesData.addAll(movieDetails)
        movieDetails.forEach { movie ->
            if (UserManager.bookmarkId.contains(movie.movieId)) {
                bookmarks.add(movie)
            }
        }
        removeDuplicates()
        return movieDetails
    }

    @RequiresApi(Build.VERSION_CODES.N)
    suspend fun getRecommendMovies(movieId: Int): List<MovieDetails> {
        var movieDetails: List<MovieDetails>
        if (isInternetAvailable(context.applicationContext)) {
            movieDetails = getRecommendMoviesAPI(movieId)
        } else {
            movieDetails = getRecommendMoviesDb()
        }
        UserManager.allMoviesData.addAll(movieDetails)
        movieDetails.forEach { movie ->
            if (UserManager.bookmarkId.contains(movie.movieId)) {
                bookmarks.add(movie)
            }
        }
        removeDuplicates()
        return movieDetails
    }

    @RequiresApi(Build.VERSION_CODES.N)
    suspend fun getTrendMovies(): List<MovieDetails> {
        var movieDetails: List<MovieDetails>
        if (isInternetAvailable(context.applicationContext)) {
            movieDetails = getTrendMoviesAPI()
        } else {
            movieDetails = getTrendMoviesDb()
        }
        UserManager.allMoviesData.addAll(movieDetails)
        movieDetails.forEach { movie ->
            if (UserManager.bookmarkId.contains(movie.movieId)) {
                bookmarks.add(movie)
            }
        }
        removeDuplicates()
        return movieDetails
    }

    suspend fun getGenres() {
        if (isInternetAvailable(context.applicationContext)) {
            getGenresAPI()
        } else {
            readGenre = genreDao.readGenre() as ArrayList<GenreDetails>
        }
    }

    suspend fun getAllMoviesAPI(): List<MovieDetails> {
        return withContext(Dispatchers.IO) {
            val result = MovieDBClient.retrofitService.getAllMovies()
            if (result.isSuccessful) {
                moviesDatabase.movieDao().addMovie(result.body()?.movies ?: ArrayList())
                result.body()?.movies ?: ArrayList()
            } else {
                ArrayList()
            }
        }
    }

    suspend fun getMovie(movieId: Int): MovieDetails? {
        val result = MovieDBClient.retrofitService.getMovieDetails(movieId)
        var movieObj: MovieDetails? = null
        if (result.isSuccessful) {
            movieObj = result.body()
        }
        return movieObj
    }

    suspend fun getGenresAPI() {
        return withContext(Dispatchers.IO) {
            val result = MovieDBClient.retrofitService.getGenreList()
            (if (result.isSuccessful) {
                moviesDatabase.genreDao().addGenre(result.body()?.genres ?: ArrayList())
                readGenre = result.body()?.genres ?: ArrayList()
            })
        }
    }

    suspend fun getRecommendMoviesAPI(movieId: Int): List<MovieDetails> {
        return withContext(Dispatchers.IO) {
            val result = MovieDBClient.retrofitService.getRecommendMovies(movieId)
            val moviesList: ArrayList<RecommendMovies> = ArrayList()
            if (result.isSuccessful) {
                var movieObj = result.body()?.movies
                if (movieObj != null) {
                    for (movie in movieObj) {
                        var recommendMovies = RecommendMovies(
                            movie.movieId,
                            movie.movieTitle,
                            movie.movieRating,
                            movie.movieDesc,
                            movie.coverImageUrl,
                            movie.genreId
                        )
                        moviesList.add(recommendMovies)
                    }
                    moviesDatabase.movieDao().addMovieRecommend(moviesList)
                }
                result.body()?.movies ?: ArrayList()
            } else {
                ArrayList()
            }
        }
    }

    suspend fun getTrendMoviesAPI(): List<MovieDetails> {
        return withContext(Dispatchers.IO) {
            val result = MovieDBClient.retrofitService.getTrendMovies()
            val moviesList: ArrayList<TrendMovies> = ArrayList()
            if (result.isSuccessful) {
                var movieObj = result.body()?.movies
                if (movieObj != null) {
                    for (movie in movieObj) {
                        var trendMovies = TrendMovies(
                            movie.movieId,
                            movie.movieTitle,
                            movie.movieRating,
                            movie.movieDesc,
                            movie.coverImageUrl,
                            movie.genreId
                        )
                        moviesList.add(trendMovies)
                    }
                    moviesDatabase.movieDao().addMovieTrend(moviesList)
                }
                result.body()?.movies ?: ArrayList()
            } else {
                ArrayList()
            }
        }
    }

    fun isInternetAvailable(context: Context): Boolean {
        return context?.let {
            (it.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo?.let { networkInfo ->
                networkInfo.isAvailable && networkInfo.isConnected
            } ?: false
        } ?: false
    }

    private suspend fun removeDuplicates() {
        val iterator = bookmarks.iterator()
        while (iterator.hasNext()) {
            val element = iterator.next()
            iterator.forEach {
                if (it.movieId == element.movieId) {
                    iterator.remove()
                }
            }
        }
        updateBookmarkList()
    }

    private suspend fun updateBookmarkList() {
        UserManager.bookmarkList.clear()
        withContext(Dispatchers.IO) {
            bookmarks.forEach {
                if (!UserManager.bookmarkList.contains(it)) {
                    UserManager.bookmarkList.add(it)
                }
            }
        }
    }
}