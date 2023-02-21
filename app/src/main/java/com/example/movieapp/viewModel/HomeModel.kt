package com.example.movieapp.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.Datasource
import com.example.movieapp.model.MovieDetails
import kotlinx.coroutines.launch

class HomeModel : ViewModel() {
    private val movieValues : MutableLiveData<ArrayList<MovieDetails>> = MutableLiveData()
    val movieData : MutableLiveData<ArrayList<MovieDetails>> = movieValues
    val movieList = Datasource()
    init {
        viewModelScope.launch {
            movieData.postValue(movieList.loadMovieList() as ArrayList<MovieDetails>?)
        }

    }


}