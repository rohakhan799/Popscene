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
    var textViewTime: TextView = view.findViewById(R.id.item_time)
    var textViewGenre: TextView = view.findViewById(R.id.item_genre)
    var imageView: ImageView = view.findViewById(R.id.item_image)
    var imageButton: ImageView = view.findViewById(R.id.imageButton_bookmark)

    fun bindView(
        movie: MovieDetails,
        listener: (MovieDetails, ImageView) -> Unit,
        bookmarkListener: (MovieDetails) -> Unit
    ) {
        var imageVi: ImageView = imageView.findViewById(R.id.item_image);

        Glide.with(this.view)
            .load(movie.coverImageUrl)
            .skipMemoryCache(false)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imageVi)

        textViewtitle.text = movie.movieTitle
        textViewImdb.text = movie.movieRating.toString()
        textViewTime.text = getDuration(movie.movieDuration)
        textViewGenre.text = getGenre(movie.genreId)
        ViewCompat.setTransitionName(imageVi, movie.movieTitle);
        itemView.setOnClickListener { listener(movie, imageView) }
        //TODO: no need for setBookmark. Use contains logic
        if (UserManager.bookmarkList.contains(movie)) {
            imageButton.setImageResource(R.drawable.bookmark2)
        } else {
            imageButton.setImageResource(R.drawable.bookmark)
        }
        imageButton.setOnClickListener {
            bookmarkListener(movie)
            //TODO: Use method to call onBindViewHolder
        }
    }

    fun getGenre(genreId: String): String {
        var genreList = UserManager.genreMap
        var GenreName: String = ""
        for (genre in genreList) {
            var genreIdArray = genreId.split(",").map { it.toInt() }
            for (id in genreIdArray) {
                if (id == genre.key) {
                    GenreName = GenreName + " " + genre.value
                }
            }
        }
        return GenreName
    }

    fun getDuration(time: Long): String {
        var movieDur: String = ""
        val hours: Long = time / 60
        val minutes: Long = time % 60
        movieDur = hours.toString() + "h " + minutes.toString() + "m "
        return movieDur
    }
}