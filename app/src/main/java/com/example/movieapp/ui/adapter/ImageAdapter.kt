package com.example.movieapp.ui.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("url")
fun ImageView.loadImage(url: String) {
    Glide.with(this.context)
        .load(url)
        .into(this)
}