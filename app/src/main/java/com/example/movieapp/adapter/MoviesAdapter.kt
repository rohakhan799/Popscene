package com.example.movieapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.database.MovieDetails
import com.example.movieapp.model.UserManager
import com.example.movieapp.viewholder.BookmarkViewHolder
import com.example.movieapp.viewholder.HomeViewHolder
import com.example.movieapp.viewholder.RecommendedViewHolder
import com.example.movieapp.viewholder.TrendViewHolder

open class MoviesAdapter(
    private val dataset: List<MovieDetails>,
    val listener: (MovieDetails, ImageView) -> Unit,
    val onBookmarkClick: (MovieDetails) -> Unit,
    val itemType: Int
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

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
            (holder as RecommendedViewHolder).bindView(dataset[position], listener) { movie ->
                if (!UserManager.bookmarkList.contains(movie)) {
                    UserManager.bookmarkList.add(movie)
                } else {
                    UserManager.bookmarkList.remove(movie)
                }
                notifyItemChanged(position)
            }
        } else if (holder is HomeViewHolder) {
            (holder as HomeViewHolder).bindView(dataset[position])
        } else if (holder is BookmarkViewHolder) {
            (holder as BookmarkViewHolder).bindView(dataset[position], listener) { movie ->
                UserManager.bookmarkList.remove(dataset[position])
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, getItemCount());
                onBookmarkClick(movie)
            }
        } else {
            (holder as TrendViewHolder).bindView(dataset[position], listener) { movie ->
                if (!UserManager.bookmarkList.contains(movie)) {
                    UserManager.bookmarkList.add(movie)

                } else {
                    UserManager.bookmarkList.remove(movie)
                }
                notifyItemChanged(position)
            }
        }
    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}
