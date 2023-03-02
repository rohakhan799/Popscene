package com.example.movieapp.ui.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.*
import com.example.movieapp.model.MovieDetails
import com.example.movieapp.ui.activity.MovieDetailActivity
import com.example.movieapp.ui.adapter.MoviesAdapter
import com.example.movieapp.viewModel.MoviesViewModel
import com.mig35.carousellayoutmanager.CarouselLayoutManager
import com.mig35.carousellayoutmanager.CarouselZoomPostLayoutListener
import com.mig35.carousellayoutmanager.CenterScrollListener


class HomeFragment : Fragment() {

    companion object {
        const val INTENT_PARCELABLE = "OBJECT_INTENT"
    }

    lateinit var recyclerView: RecyclerView
    lateinit var recyclerViewtrend: RecyclerView
    private lateinit var adapter: MoviesAdapter
    private lateinit var adapterRecommend: MoviesAdapter
    private lateinit var adapterTrend: MoviesAdapter
    lateinit var movieViewModel: MoviesViewModel

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view: View = inflater.inflate(R.layout.fragment_home, container, false)
        movieViewModel = ViewModelProvider(this)[MoviesViewModel::class.java]
        movieViewModel.init(requireActivity().application)
        movieViewModel.fetchAllMovies()
        movieViewModel.fetchRecommendedMovies()
        movieViewModel.fetchTrendingMovies()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var myDataset: List<MovieDetails> = movieViewModel.allMovies
        adapter = MoviesAdapter(myDataset, listener = { movie, image ->
            val intent: Intent = Intent(activity, MovieDetailActivity::class.java)
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

        recyclerView = view.findViewById(R.id.recycler_view_recommend)
        recyclerView.setHasFixedSize(true)

        recyclerView.layoutManager =
            LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = MoviesAdapter(movieViewModel.movieRecommend, listener = { movie, image ->
            val intent: Intent = Intent(activity, MovieDetailActivity::class.java)
            intent.putExtra(INTENT_PARCELABLE, movie)
            intent.putExtra("Shared Image", ViewCompat.getTransitionName(image));
            val options: ActivityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                requireActivity(),
                image,
                ViewCompat.getTransitionName(image)!!
            )
            startActivityForResult(intent, 1, options.toBundle())
        }, onBookmarkClick = {}, 1)
        adapterRecommend = recyclerView.adapter as MoviesAdapter

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
            }, onBookmarkClick = {}, 2
        )
        adapterTrend = recyclerViewtrend.adapter as MoviesAdapter

        movieViewModel.allMoviesLiveData.observe(viewLifecycleOwner) { movies ->
            adapter.updateData(movies)
        }
        movieViewModel.trendLiveData.observe(viewLifecycleOwner) { movies ->
            adapterRecommend.updateData(movies)
        }
        movieViewModel.recommendLiveData.observe(viewLifecycleOwner) { movies ->
            adapterTrend.updateData(movies)
        }
    }

/*    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode === 1) {
          if (resultCode === RESULT_OK) {
                val result = data!!.getParcelableExtra("result", MovieDetails<Int>(movieId = ))
                mTextViewResult.setText("" + result)
            }
            if (resultCode === RESULT_CANCELED) {
                mTextViewResult.setText("Nothing selected")
            }
        }
            lateinit var rec: MoviesAdapter
           rec.notifyMovieId(movie.movieId)
    }*/
}