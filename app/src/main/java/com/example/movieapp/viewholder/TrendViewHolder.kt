package com.example.movieapp.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.movieapp.R
import com.example.movieapp.model.MovieDetails
import com.example.movieapp.model.UserManager

open class TrendViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    var textViewtitle: TextView = view.findViewById(R.id.item_title_trend)
    var textViewImdb: TextView = view.findViewById(R.id.item_imdb_trend)
    var textViewTime: TextView = view.findViewById(R.id.item_time_trend)
    var textViewGenre: TextView = view.findViewById(R.id.item_genre_trend)
    var imageViewtrend: ImageView = view.findViewById(R.id.item_image_trend)
    var textViewDesc: TextView = view.findViewById(R.id.item_Desc_trend)
    var imageButton: ImageView = view.findViewById(R.id.imageButton_bookmark_trend)


    fun bindView(
        movie: MovieDetails,
        listener: (MovieDetails, ImageView) -> Unit,
        bookmarkListener: (MovieDetails) -> Unit
    ) {
        var imageVi: ImageView = imageViewtrend.findViewById(R.id.item_image_trend);
        Glide.with(this.view)
            .load(movie.coverImageUrl)
            .skipMemoryCache(false)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imageVi)

        textViewtitle.text = movie.movieTitle
        textViewImdb.text = movie.movieRating
        textViewTime.text = movie.movieTime
        textViewGenre.text = movie.movieGenre
        textViewDesc.text = movie.movieDesc
        ViewCompat.setTransitionName(imageVi, movie.movieTitle);

        itemView.setOnClickListener { listener(movie, imageViewtrend) }

        if (UserManager.bookmarkList.contains(movie)) {
            imageButton.setImageResource(R.drawable.bookmark2)
        } else {
            imageButton.setImageResource(R.drawable.bookmark)
        }
        imageButton.setOnClickListener {
            bookmarkListener(movie)
        }
    }
}