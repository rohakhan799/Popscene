package com.example.movieapp.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.model.MovieDetails
import kotlinx.coroutines.launch

class HomeModel : ViewModel() {
    lateinit var movieViewModel: MoviesViewModel
    private val movieValues : MutableLiveData<ArrayList<MovieDetails>> = MutableLiveData()
    val movieData : MutableLiveData<ArrayList<MovieDetails>> = movieValues
    val movieList = movieViewModel.allMovies
    init {
        viewModelScope.launch {
            movieData.postValue(movieList as ArrayList<MovieDetails>?)
        }

    }


}