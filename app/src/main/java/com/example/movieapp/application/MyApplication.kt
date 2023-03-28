package com.example.movieapp.application

import android.app.Application
import com.example.movieapp.ui.component.DaggerMovieComponent

class MyApplication  : Application() {
    val appComponent by lazy {
        DaggerMovieComponent.builder().application(this).build()
    }

    override fun onCreate() {
        super.onCreate()

    }
}