package com.example.movieapp.ui.viewholder

import android.view.View
import android.widget.ImageView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.movieapp.R
import com.example.movieapp.model.MovieDetails

class HomeViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    var imageViewSlider: ImageView = view.findViewById(R.id.movie_slider_Img)

    fun bindView(
        movie: MovieDetails,
        listener: (MovieDetails, ImageView) -> Unit
    ) {
        var imageVi: ImageView = imageViewSlider.findViewById(R.id.movie_slider_Img);
        Glide.with(this.view)
            .load(movie.coverImageUrl)
            .skipMemoryCache(false)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imageVi)

        ViewCompat.setTransitionName(imageVi, movie.movieTitle);
        itemView.setOnClickListener { listener(movie, imageViewSlider) }
    }
}