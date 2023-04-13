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
import com.example.movieapp.model.BookMarkDetails
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

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        lifecycleScope.launch {
            movieViewModel.init()
            movieViewModel.fetchTrendingMovies()
        }

        var textViewGenre: TextView = binding.genreDetails
        var imageView: ImageView = binding.titleImg

        movie = intent.getParcelableExtra(HomeFragment.INTENT_PARCELABLE)

        if (movie != null) {
            imageView.load("https://image.tmdb.org/t/p/w342" + movie?.coverImageUrl)
        }
        imageView.transitionName = movie?.movieTitle
        if (movie != null) {
            binding.titleDetails.text = movie?.movieTitle
        }
        if (movie != null) {
            binding.imdbDetails.text = movie?.movieRating.toString()
        }
        if (movie != null) {
            textViewGenre.text = getGenre(movie!!.genreId)
        }
        if (movie != null) {
            binding.descDetails.text = movie?.movieDesc
        }

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
            }, onBookmarkClick = { movie ->
                if (!UserManager.bookmarkList.contains(movie)) {
                    UserManager.bookmarkList.add(movie)
                    UserManager.bookmarkId.add(movie.movieId)
                    addBookmark(movie.movieId)
                } else {
                    UserManager.bookmarkId.remove(movie.movieId)
                    UserManager.bookmarkList.remove(movie)
                    removeBookmark(movie.movieId)
                }
                movieViewModel.bookmarkLiveData.observe(this) {
                    adapterTrend.notifyDataSetChanged()
                }
            }, 2)
        adapterTrend = recyclerViewtrend.adapter as MoviesAdapter

        var imageButton: ImageView = findViewById(com.example.movieapp.R.id.bookmark_moviedetail)
        if (movie?.let { UserManager.bookmarkId.contains(it.movieId) } == true) {
            imageButton.setImageResource(com.example.movieapp.R.drawable.bookmark2)
        } else {
            imageButton.setImageResource(com.example.movieapp.R.drawable.bookmark)
        }

        imageButton.setOnClickListener {
            if (movie != null) {
                if ((!UserManager.bookmarkId.contains(movie?.movieId))) {
                    UserManager.bookmarkId.add(movie!!.movieId)
                    UserManager.bookmarkList.add(movie!!)
                    addBookmark(movie!!.movieId)
                    imageButton.setImageResource(com.example.movieapp.R.drawable.bookmark2)
                } else {
                    UserManager.bookmarkId.remove(movie!!.movieId)
                    UserManager.bookmarkList.remove(movie!!)
                    removeBookmark(movie!!.movieId)
                    imageButton.setImageResource(com.example.movieapp.R.drawable.bookmark)
                }
            }
        }
        movieViewModel.trendLiveData.observe(this) { movies ->
            adapterTrend.updateData(movies)
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

    fun addBookmark(movieId: Int) {
        val bookmarkObj = UserManager.userObj?.let { BookMarkDetails(0, it.userId, movieId) }
        if (bookmarkObj != null) {
            movieViewModel.addBookmark(bookmarkObj)
        }
    }

    fun removeBookmark(movieId: Int) {
        UserManager.userObj?.let { movieViewModel.removeBookmark(it.userId, movieId) }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val resultIntent = Intent(this, HomeFragment::class.java)
        if (movie != null) {
            resultIntent.putExtra("result", movie)
            setResult(RESULT_OK, resultIntent)
        }
    }
}