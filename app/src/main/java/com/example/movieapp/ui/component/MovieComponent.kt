package com.example.movieapp.ui.component

import android.app.Activity
import android.app.Application
import com.example.movieapp.application.MyApplication
import com.example.movieapp.data.modules.MoviesModule
import com.example.movieapp.repository.MoviesRepository
import com.example.movieapp.viewModel.MoviesViewModel
import com.example.movieapp.viewModel.ViewModelFactory
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(modules = [MoviesModule::class])
@Singleton
interface MovieComponent {
    fun inject(application: MyApplication)

    fun inject(activity: Activity)

    fun viewModel(): MoviesViewModel

    fun viewModelsFactory(): ViewModelFactory

    fun repository(): MoviesRepository


    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): MovieComponent
    }
}