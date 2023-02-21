package com.example.movieapp.adapter

import android.widget.ImageView
import com.example.movieapp.database.MovieDetails

class HomeAdapter(
    dataset: List<MovieDetails>,
    listener: (MovieDetails, ImageView) -> Unit,
    onBookmarkClick: (MovieDetails) -> Unit,
    itemType: Int
) : MoviesAdapter(dataset, listener, onBookmarkClick, itemType) {
    override fun getItemViewType(position: Int): Int {
        return MoviesAdapter.VIEW_TYPE_HOME
    }
}