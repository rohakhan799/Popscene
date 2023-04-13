package com.example.movieapp.ui.viewholder

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

class RecommendedViewHolder(private val view: View) :
    RecyclerView.ViewHolder(view) {
    var textViewtitle: TextView = view.findViewById(R.id.item_title)
    var textViewImdb: TextView = view.findViewById(R.id.item_imdb)
    var textViewGenre: TextView = view.findViewById(R.id.item_genre)
    var imageView: ImageView = view.findViewById(R.id.item_image)
    var imageButton: ImageView = view.findViewById(R.id.imageButton_bookmark)

    fun bindView(
        movie: MovieDetails,
        listener: (MovieDetails, ImageView) -> Unit,
        bookmarkListener: (MovieDetails) -> Unit
    ) {
        var imageVi: ImageView = imageView.findViewById(R.id.item_image);
        val POSTER_BASE_URL = "https://image.tmdb.org/t/p/w342"

        Glide.with(this.view)
            .load(POSTER_BASE_URL + movie.coverImageUrl)
            .skipMemoryCache(false)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imageVi)

        textViewtitle.text = movie.movieTitle
        textViewImdb.text = movie.movieRating.toString()
        textViewGenre.text = getGenre(movie.genreId)
        ViewCompat.setTransitionName(imageVi, movie.movieTitle);
        itemView.setOnClickListener { listener(movie, imageView) }

        if (UserManager.bookmarkId.contains(movie.movieId)) {
            imageButton.setImageResource(R.drawable.bookmark2)
        } else {
            imageButton.setImageResource(R.drawable.bookmark)
        }

        imageButton.setOnClickListener {
            bookmarkListener(movie)
        }
    }

    fun getGenre(genresId: ArrayList<String>): String {
        val ints = genresId.map { it.toInt() }.toTypedArray()
        val filteredGenres = UserManager.readGenre.filter { genre ->
            ints.contains(genre.genreId)
        }
        val genreNames = filteredGenres.map { genre ->
            genre.genreName
        }
        return genreNames.joinToString(", ")
    }

}