package com.example.movieapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.model.MovieDetails
import com.example.movieapp.model.UserDetails
import com.example.movieapp.ui.viewholder.BookmarkViewHolder
import com.example.movieapp.ui.viewholder.HomeViewHolder
import com.example.movieapp.ui.viewholder.RecommendedViewHolder
import com.example.movieapp.ui.viewholder.TrendViewHolder

open class MoviesAdapter(
    var dataset: List<MovieDetails>,
    val listener: (MovieDetails, ImageView) -> Unit,
    val onBookmarkClick: (MovieDetails) -> Unit,
    val itemType: Int
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var userList = ArrayList<UserDetails>()

    companion object {
        const val VIEW_TYPE_RECOMMENDED = 1
        const val VIEW_TYPE_TRENDING = 2
        const val VIEW_TYPE_HOME = 4
        const val VIEW_TYPE_BOOKMARK = 3
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {

        return if (itemType == 1) {
            RecommendedViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.recommend_list, parent, false)
            )
        } else if (itemType == 2) {
            TrendViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.trend_list, parent, false)
            )
        } else if (itemType == 3) {
            BookmarkViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.trend_list, parent, false)
            )
        } else {
            HomeViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.movie_slider, parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is RecommendedViewHolder) {
            (holder as RecommendedViewHolder).bindView(dataset[position], listener, onBookmarkClick)
        } else if (holder is HomeViewHolder) {
            (holder as HomeViewHolder).bindView(dataset[position], listener)
        } else if (holder is BookmarkViewHolder) {
            (holder as BookmarkViewHolder).bindView(dataset[position], listener, onBookmarkClick)
        } else {
            (holder as TrendViewHolder).bindView(dataset[position], listener, onBookmarkClick)
        }
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    fun updateData(movie: List<MovieDetails>) {
        dataset = movie
        notifyDataSetChanged()
    }
}
