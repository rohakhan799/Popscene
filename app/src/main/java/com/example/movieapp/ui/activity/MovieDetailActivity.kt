package com.example.movieapp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.example.movieapp.application.MyApplication
import com.example.movieapp.databinding.ActivityMovieDetailBinding
import com.example.movieapp.model.GenreDetails
import com.example.movieapp.model.MovieDetails
import com.example.movieapp.model.UserManager
import com.example.movieapp.ui.adapter.MoviesAdapter
import com.example.movieapp.ui.fragment.HomeFragment
import com.example.movieapp.viewModel.MovieDetailViewModel
import kotlinx.coroutines.launch


class MovieDetailActivity : AppCompatActivity() {
    var movie: MovieDetails? = null
    private val movieViewModel: MovieDetailViewModel by viewModels {
        (application as MyApplication).appComponent.viewModelsFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        val binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        lateinit var adapterTrend: MoviesAdapter
        setContentView(binding.root)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        //  movieViewModel = ViewModelProvider(this)[MovieDetailViewModel::class.java]
        lifecycleScope.launch {
            movieViewModel.init()
            movieViewModel.fetchTrendingMovies()
        }

        var textViewTime: TextView = binding.timeDetails
        var textViewGenre: TextView = binding.genreDetails
        var imageView: ImageView = binding.titleImg

        movie = intent.getParcelableExtra<MovieDetails>(HomeFragment.INTENT_PARCELABLE)
        if (movie != null) {
            imageView.load(movie?.coverImageUrl)
        }
        if (movie != null) {
            binding.titleDetails.text = movie?.movieTitle
        }
        if (movie != null) {
            binding.imdbDetails.text = movie?.movieRating.toString()
        }
        if (movie != null) {
            textViewTime.text = getDuration(movie!!.movieDuration)
        }
        if (movie != null) {
            textViewGenre.text = getGenre(movie!!.genres)
        }

        if (movie != null) {
            binding.descDetails.text = movie?.movieDesc
        }
        imageView.transitionName = movie?.movieTitle

        val myDatasetTrend = movieViewModel.movieTrend
        val recyclerViewtrend: RecyclerView =
            findViewById(com.example.movieapp.R.id.recycler_view_details)
        recyclerViewtrend.setHasFixedSize(true)

        recyclerViewtrend.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerViewtrend.adapter =
            MoviesAdapter(myDatasetTrend, listener = { movie, image ->
                val intent2: Intent = Intent(this, MovieDetailActivity::class.java)
                intent2.putExtra(HomeFragment.INTENT_PARCELABLE, movie)
                intent2.putExtra("Shared Trend Image", ViewCompat.getTransitionName(image));
                val options: ActivityOptionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        this,
                        image,
                        ViewCompat.getTransitionName(image)!!
                    )
                startActivityForResult(intent2, 1, options.toBundle())
            }, onBookmarkClick = {}, 2)
        adapterTrend = recyclerViewtrend.adapter as MoviesAdapter

        var imageButton: ImageView = findViewById(com.example.movieapp.R.id.bookmark)
        if (UserManager.bookmarkList.contains(movie)) {
            imageButton.setImageResource(com.example.movieapp.R.drawable.bookmark2)
        } else {
            imageButton.setImageResource(com.example.movieapp.R.drawable.bookmark)
        }

        imageButton.setOnClickListener {
            if (movie != null) {
                if ((!UserManager.bookmarkList.contains(movie))) {
                    UserManager.bookmarkList.add(movie!!)
                    imageButton.setImageResource(com.example.movieapp.R.drawable.bookmark2)
                } else {
                    UserManager.bookmarkList.remove(movie)
                    imageButton.setImageResource(com.example.movieapp.R.drawable.bookmark)
                }
            }
        }

        movieViewModel.trendLiveData.observe(this) { movies ->
            adapterTrend.updateData(movies)
        }
    }

    fun getGenre(genres: List<GenreDetails>): String {
        return genres.joinToString(", ") {
            it.genreName
        }
    }

    fun getDuration(time: Long): String {
        var movieDur: String = ""
        val hours: Long = time / 60
        val minutes: Long = time % 60
        movieDur = hours.toString() + "h " + minutes.toString() + "m "
        return movieDur
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val resultIntent = Intent(this, HomeFragment::class.java)
        if (movie != null) {
            resultIntent.putExtra("result", movie)
            setResult(RESULT_OK, resultIntent)
            //startActivity(resultIntent)
        }
    }
}