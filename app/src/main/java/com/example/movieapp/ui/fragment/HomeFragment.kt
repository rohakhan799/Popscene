package com.example.movieapp.ui.fragment

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.*
import com.example.movieapp.application.MyApplication
import com.example.movieapp.databinding.ActivityLoginBinding
import com.example.movieapp.model.BookMarkDetails
import com.example.movieapp.model.MovieDetails
import com.example.movieapp.model.UserManager
import com.example.movieapp.model.UserManager.userObj
import com.example.movieapp.ui.activity.MovieDetailActivity
import com.example.movieapp.ui.adapter.MoviesAdapter
import com.example.movieapp.viewModel.MoviesViewModel
import com.facebook.shimmer.ShimmerFrameLayout
import com.mig35.carousellayoutmanager.CarouselLayoutManager
import com.mig35.carousellayoutmanager.CarouselZoomPostLayoutListener
import com.mig35.carousellayoutmanager.CenterScrollListener
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    companion object {
        const val INTENT_PARCELABLE = "OBJECT_INTENT"
    }

    lateinit var recyclerView: RecyclerView
    lateinit var recyclerViewtrend: RecyclerView
    private lateinit var adapter: MoviesAdapter
    private lateinit var adapterRecommend: MoviesAdapter
    private lateinit var adapterTrend: MoviesAdapter
    private val movieViewModel: MoviesViewModel by viewModels {
        (requireActivity().application as MyApplication).appComponent.viewModelsFactory()
    }
    private lateinit var shimmer: ShimmerFrameLayout
    private lateinit var shimmer_trend: ShimmerFrameLayout

    var recommendTextView = view?.findViewById<TextView>(R.id.textView_recommend)
    var trendTextView = view?.findViewById<TextView>(R.id.textView_trend)

    lateinit var bindingData: ActivityLoginBinding

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view: View = inflater.inflate(R.layout.fragment_home, container, false)
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        bindingData = binding

        lifecycleScope.launch {
            movieViewModel.init()
            movieViewModel.fetchAllMovies()
            movieViewModel.fetchRecommendedMovies(1033219)
            movieViewModel.fetchTrendingMovies()
            movieViewModel.fetchAllBookmarkMovies()

        }
        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    activity!!.finish()
                    val sharedPreferencesCache = activity!!.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
                    sharedPreferencesCache.edit().putLong("last_update", System.currentTimeMillis()).apply()
                    val editor = sharedPreferencesCache.edit()
                    editor.remove("cached_data_key")
                    editor.apply()
                }
            })
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var recyclerViewTest: View = view.findViewById(R.id.no_movies)

        var myDataset: List<MovieDetails> = movieViewModel.allMovies
        var myDatasetRecommend: List<MovieDetails> = movieViewModel.movieRecommend
        var myDatasetTrend: List<MovieDetails> = movieViewModel.movieTrend


        adapter = MoviesAdapter(myDataset, listener = { movie, image ->
            val intent = Intent(activity, MovieDetailActivity::class.java)
            intent.putExtra(INTENT_PARCELABLE, movie)
            intent.putExtra("Slider Image", ViewCompat.getTransitionName(image));
            val options: ActivityOptionsCompat =
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                    requireActivity(),
                    image,
                    ViewCompat.getTransitionName(image)!!
                )
            startActivityForResult(intent, 1, options.toBundle())
        }, onBookmarkClick = {}, 4)
        recyclerViewTest.visibility = View.GONE
        val carouselLayoutVertical = CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL)
        carouselLayoutVertical.setPostLayoutListener(CarouselZoomPostLayoutListener())
        carouselLayoutVertical.setCircleLayout(true)
        val carouselRecyclerView = view.findViewById<RecyclerView>(R.id.carouselRecyclerview)
        carouselRecyclerView.setHasFixedSize(true)
        carouselRecyclerView.addOnScrollListener(CenterScrollListener())
        carouselRecyclerView.layoutManager = carouselLayoutVertical
        carouselRecyclerView.addOnScrollListener(CenterScrollListener())
        carouselRecyclerView.adapter = adapter

        recyclerView = view.findViewById(R.id.recycler_view_recommend)
        shimmer = view.findViewById(R.id.recommend_layout)
        shimmer.startShimmer()

        Handler(Looper.getMainLooper()).postDelayed({
            recyclerView.setHasFixedSize(true)
            recyclerView.layoutManager =
                LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
            recyclerView.adapter =
                MoviesAdapter(
                    myDatasetRecommend,
                    listener = { movie, image ->
                        val intent = Intent(activity, MovieDetailActivity::class.java)
                        intent.putExtra(INTENT_PARCELABLE, movie)
                        intent.putExtra("Shared Image", ViewCompat.getTransitionName(image));
                        val options: ActivityOptionsCompat =
                            ActivityOptionsCompat.makeSceneTransitionAnimation(
                                requireActivity(),
                                image,
                                ViewCompat.getTransitionName(image)!!
                            )
                        startActivityForResult(intent, 1, options.toBundle())
                    },
                    onBookmarkClick = { movie ->
                        if (!UserManager.bookmarkId.contains(movie.movieId)) {
                            UserManager.bookmarkList.add(movie)
                            UserManager.bookmarkId.add(movie.movieId)
                            addBookmark(movie.movieId)
                        } else {
                            UserManager.bookmarkList.remove(movie)
                            UserManager.bookmarkId.remove(movie.movieId)
                            removeBookmark(movie.movieId)
                        }
                        movieViewModel.bookmarkLiveData.observe(viewLifecycleOwner) {
                            adapterRecommend.notifyDataSetChanged()
                        }
                    }, 1
                )
            adapterRecommend = recyclerView.adapter as MoviesAdapter
            movieViewModel.recommendLiveData.observe(viewLifecycleOwner) { movies ->
                shimmer.stopShimmer()
                shimmer.visibility = View.GONE
                adapterRecommend.updateData(movies)
                if (movies.isEmpty()) {
                    recyclerView.visibility = View.GONE
                    recommendTextView?.visibility = View.GONE
                }
                if ((recyclerView.visibility == View.GONE) && ((carouselRecyclerView.visibility == View.GONE)) && ((recyclerViewtrend.visibility == View.GONE))) {
                    recyclerViewTest.visibility = View.VISIBLE
                }
            }
        }, 2000)

        recyclerViewtrend = view.findViewById(R.id.recycler_view_trend)
        shimmer_trend = view.findViewById(R.id.trend_layout)
        shimmer_trend.startShimmer()

        Handler(Looper.getMainLooper()).postDelayed({

            recyclerViewtrend.setHasFixedSize(true)
            recyclerViewtrend.layoutManager =
                LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
            recyclerViewtrend.adapter = MoviesAdapter(
                myDatasetTrend,
                listener = { movie, image ->
                    val intent2: Intent = Intent(activity, MovieDetailActivity::class.java)
                    intent2.putExtra(INTENT_PARCELABLE, movie)
                    intent2.putExtra("Shared Trend Image", ViewCompat.getTransitionName(image));
                    val options: ActivityOptionsCompat =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(
                            requireActivity(),
                            image,
                            ViewCompat.getTransitionName(image)!!
                        )
                    startActivityForResult(intent2, 1, options.toBundle())
                }, onBookmarkClick = { movie ->
                    if (!UserManager.bookmarkId.contains(movie.movieId)) {
                        UserManager.bookmarkList.add(movie)
                        UserManager.bookmarkId.add(movie.movieId)
                        addBookmark(movie.movieId)
                    } else {
                        UserManager.bookmarkList.remove(movie)
                        UserManager.bookmarkId.remove(movie.movieId)
                        removeBookmark(movie.movieId)
                    }
                    movieViewModel.bookmarkLiveData.observe(viewLifecycleOwner) {
                        adapterTrend.notifyDataSetChanged()
                    }
                }, 2
            )
            adapterTrend = recyclerViewtrend.adapter as MoviesAdapter
            movieViewModel.trendLiveData.observe(viewLifecycleOwner) { movies ->
                shimmer_trend.stopShimmer()
                shimmer_trend.visibility = View.GONE
                adapterTrend.updateData(movies)
                if (movies.isEmpty()) {
                    recyclerViewtrend.visibility = View.GONE
                    trendTextView?.visibility = View.GONE
                }
            }
        }, 2000)

        movieViewModel.allMoviesLiveData.observe(viewLifecycleOwner) { movies ->
            adapter.updateData(movies)
            if (movies.isEmpty()) {
                carouselRecyclerView.visibility = View.GONE
            }
        }
    }

    fun addBookmark(movieId: Int) {
        val bookmarkObj = userObj?.let { BookMarkDetails(0, it.userId, movieId) }
        if (bookmarkObj != null) {
            movieViewModel.addBookmark(bookmarkObj)
        }
    }

    fun removeBookmark(movieId: Int) {
        userObj?.let { movieViewModel.removeBookmark(it.userId, movieId) }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            var movie: MovieDetails? = data?.getParcelableExtra("result")
            if (movie?.let { isMovieTrendExists(it) } == true) {
                var imageButton: ImageView? =
                    view?.findViewById(R.id.imageButton_bookmark_trend)
                if (UserManager.bookmarkId.contains(movie.movieId)) {
                    imageButton?.setImageResource(R.drawable.bookmark2)
                } else {
                    imageButton?.setImageResource(R.drawable.bookmark)
                }
                movieViewModel.bookmarkLiveData.observe(viewLifecycleOwner) {
                    adapterTrend.notifyDataSetChanged()
                }
            } else if (movie?.let { isMovieRecommendationExists(it) } == true) {
                var imageButton: ImageView? =
                    requireActivity().findViewById(R.id.imageButton_bookmark)
                if (UserManager.bookmarkId.contains(movie.movieId)) {
                    imageButton?.setImageResource(R.drawable.bookmark2)
                } else {
                    imageButton?.setImageResource(R.drawable.bookmark)
                }
                movieViewModel.bookmarkLiveData.observe(viewLifecycleOwner) {
                    adapterRecommend.notifyDataSetChanged()
                }
            }
        }
    }

    private fun isMovieRecommendationExists(movie: MovieDetails): Boolean {
        for (recommendation in movieViewModel.movieRecommend) {
            if (recommendation.movieId == movie.movieId) {
                return true
            }
        }
        return false
    }

    private fun isMovieTrendExists(movie: MovieDetails): Boolean {
        for (recommendation in movieViewModel.movieTrend) {
            if (recommendation.movieId == movie.movieId) {
                return true
            }
        }
        return false
    }
}