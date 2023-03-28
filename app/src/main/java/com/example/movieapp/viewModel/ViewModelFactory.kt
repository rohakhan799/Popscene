package com.example.movieapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider

class ViewModelFactory @Inject constructor(
    myViewModelProvider: Provider<MoviesViewModel>,
    MovieDetailViewModelProvider: Provider<MovieDetailViewModel>,
    loginViewModelProvider: Provider<LoginViewModel>

) : ViewModelProvider.Factory {

    private val providers = mapOf<Class<*>, Provider<out ViewModel>>(
        MoviesViewModel::class.java to myViewModelProvider,
        MovieDetailViewModel::class.java to MovieDetailViewModelProvider,
        LoginViewModel::class.java to loginViewModelProvider
    )

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return providers[modelClass]!!.get() as T
    }
}