package com.example.movieapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.example.movieapp.adapter.MoviesAdapter
import com.example.movieapp.data.Datasource
import com.example.movieapp.databinding.ActivityMovieDetailBinding
import com.example.movieapp.model.MovieDetails
import com.example.movieapp.model.UserManager

class MovieDetailActivity : AppCompatActivity() {
    @SuppressLint("ResourceType")
     var movie: MovieDetails?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var textViewtitle: TextView = binding.titleDetails
        var textViewImdb: TextView = binding.imdbDetails
        var textViewTime: TextView = binding.timeDetails
        var textViewGenre: TextView = binding.genreDetails
        var imageView: ImageView = binding.titleImg
        var textViewDesc: TextView = binding.descDetails
        var textViewCast: TextView = binding.CastDetails
        var imageBtn: ImageView = binding.bookmark

        movie = intent.getParcelableExtra<MovieDetails>(HomeFragment.INTENT_PARCELABLE)
        val bundle: Bundle? = intent.extras
        if (movie != null) {
            imageView.load(movie?.coverImageUrl)
        }
        if (movie != null) {
            textViewCast.text = movie?.movieCast
        }
        if (movie != null) {
            textViewtitle.text = movie?.movieTitle
        }
        if (movie != null) {
            textViewImdb.text = movie?.movieRating
        }
        if (movie != null) {
            textViewTime.text = movie?.movieTime
        }
        if (movie != null) {
            textViewGenre.text = movie?.movieGenre
        }
        if (movie != null) {
            textViewDesc.text = movie?.movieDesc
        }
        imageView.transitionName = movie?.movieTitle

        val myDataset_trend = Datasource().loadMovieList()
        val recyclerViewtrend: RecyclerView = findViewById(R.id.recycler_view_details)
        recyclerViewtrend.setHasFixedSize(true)

        recyclerViewtrend.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerViewtrend.adapter = MoviesAdapter(myDataset_trend, listener = { movie, image ->
            val intent2: Intent = Intent(this, MovieDetailActivity::class.java)
            intent2.putExtra(HomeFragment.INTENT_PARCELABLE, movie)
            intent2.putExtra("Shared Trend Image", ViewCompat.getTransitionName(image));
            val options: ActivityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this,
                image,
                ViewCompat.getTransitionName(image)!!
            )
            startActivityForResult(intent2, 1, options.toBundle())
        }, onBookmarkClick = {},2 )

        var imageButton: ImageView = findViewById(R.id.bookmark)
        if (UserManager.bookmarkList.contains(movie)) {
            imageButton.setImageResource(R.drawable.bookmark2)
        } else {
            imageButton.setImageResource(R.drawable.bookmark)
        }
        val resultIntent = Intent()

        imageButton.setOnClickListener {
            if (movie != null) {
                //TODO: Check if already exists and remove if it does
                if ((!UserManager.bookmarkList.contains(movie))) {
                    UserManager.bookmarkList.add(movie!!)
                    imageButton.setImageResource(R.drawable.bookmark2)
                } else {
                    UserManager.bookmarkList.remove(movie)
                    imageButton.setImageResource(R.drawable.bookmark)
                }
                resultIntent.putExtra("result", movie)
                setResult(RESULT_OK, resultIntent)

            }
        }
    }

    override fun onBackPressed() {
        if (movie != null) {
        }
        super.onBackPressed()
    }
}