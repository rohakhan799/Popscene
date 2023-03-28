package com.example.movieapp.ui.fragment

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
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
    lateinit var bindingData: ActivityLoginBinding

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view: View = inflater.inflate(R.layout.fragment_home, container, false)
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        bindingData = binding

        // movieViewModel = ViewModelProvider(this)[MoviesViewModel::class.java]
        lifecycleScope.launch {
            movieViewModel.init()
            movieViewModel.fetchAllMovies()
            movieViewModel.fetchRecommendedMovies()
            movieViewModel.fetchTrendingMovies()
            movieViewModel.fetchAllBookmarkMovies()
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var myDataset: List<MovieDetails> = movieViewModel.allMovies
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
        val carouselLayoutVertical = CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL)
        carouselLayoutVertical.setPostLayoutListener(CarouselZoomPostLayoutListener())
        carouselLayoutVertical.setCircleLayout(true)
        val carouselRecyclerView = view.findViewById<RecyclerView>(R.id.carouselRecyclerview)
        carouselRecyclerView.setHasFixedSize(true)
        carouselRecyclerView.addOnScrollListener(CenterScrollListener())
        carouselRecyclerView.layoutManager = carouselLayoutVertical
        carouselRecyclerView.addOnScrollListener(CenterScrollListener())
        carouselRecyclerView.adapter = adapter

        shimmer = view.findViewById(R.id.shimmer_recommend)
        shimmer.startShimmer()

        Handler(Looper.getMainLooper()).postDelayed({
            recyclerView = view.findViewById(R.id.recycler_view_recommend)
            recyclerView.setHasFixedSize(true)
            recyclerView.layoutManager =
                LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
            recyclerView.adapter =
                MoviesAdapter(
                    movieViewModel.movieRecommend,
                    listener = { movie, image ->
                        d("mytest", "onBookmarkClick ${movie.movieTitle}")
                        val intent: Intent = Intent(activity, MovieDetailActivity::class.java)
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
                        var MovieIdObj: MovieDetails? = returnMovieObj(movie)
                        if (!UserManager.bookmarkList.contains(MovieIdObj)) {
                            if (MovieIdObj != null) {
                                UserManager.bookmarkList.add(MovieIdObj)
                                addBookmark(MovieIdObj.movieId)
                            }
                        } else {
                            UserManager.bookmarkList.remove(MovieIdObj)
                            if (MovieIdObj != null) {
                                removeBookmark(MovieIdObj.movieId)
                            }
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
            }
        }, 0)

        recyclerViewtrend = view.findViewById(R.id.recycler_view_trend)
        recyclerViewtrend.setHasFixedSize(true)

        recyclerViewtrend.layoutManager =
            LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
        recyclerViewtrend.adapter = MoviesAdapter(
            movieViewModel.movieTrend,
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
                var MovieIdObj: MovieDetails? = returnMovieObj(movie)
                if (!UserManager.bookmarkList.contains(MovieIdObj)) {
                    if (MovieIdObj != null) {
                        UserManager.bookmarkList.add(MovieIdObj)
                        addBookmark(MovieIdObj.movieId)
                    }
                } else {
                    UserManager.bookmarkList.remove(MovieIdObj)
                    if (MovieIdObj != null) {
                        removeBookmark(MovieIdObj.movieId)
                    }
                }
                movieViewModel.bookmarkLiveData.observe(viewLifecycleOwner) {
                    adapterTrend.notifyDataSetChanged()
                }

            }, 2
        )
        adapterTrend = recyclerViewtrend.adapter as MoviesAdapter

        movieViewModel.allMoviesLiveData.observe(viewLifecycleOwner) { movies ->
            adapter.updateData(movies)
        }
/*        movieViewModel.recommendLiveData.observe(viewLifecycleOwner) { movies ->
            adapterRecommend.updateData(movies)
        }*/
        movieViewModel.trendLiveData.observe(viewLifecycleOwner) { movies ->
            adapterTrend.updateData(movies)
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

    fun returnMovieObj(movie: MovieDetails): MovieDetails? {
        var MovieIdObj: MovieDetails? =
            movieViewModel.allMovies.find { it.movieTitle == movie.movieTitle }
        return MovieIdObj
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            var movie: MovieDetails? = data?.getParcelableExtra("result")
            if (movieViewModel.movieTrend.contains(movie)) {
                var imageButton: ImageView? =
                    view?.findViewById(R.id.imageButton_bookmark_trend)
                if (UserManager.bookmarkList.contains(movie)) {
                    imageButton?.setImageResource(R.drawable.bookmark2)
                } else {
                    imageButton?.setImageResource(R.drawable.bookmark)
                }
            } else if (movieViewModel.movieRecommend.contains(movie)) {
                var imageButton: ImageView? =
                    requireActivity().findViewById(R.id.imageButton_bookmark)
                if (UserManager.bookmarkList.contains(movie)) {
                    imageButton?.setImageResource(R.drawable.bookmark2)
                } else {
                    imageButton?.setImageResource(R.drawable.bookmark)
                }
            }
        }
    }
}