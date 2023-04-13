package com.example.movieapp.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.application.MyApplication
import com.example.movieapp.model.UserManager
import com.example.movieapp.ui.activity.MovieDetailActivity
import com.example.movieapp.ui.adapter.MoviesAdapter
import com.example.movieapp.viewModel.MoviesViewModel
import kotlinx.coroutines.launch

class BookmarkFragment : Fragment() {
    lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MoviesAdapter

    private val movieViewModel: MoviesViewModel by viewModels {
        (requireActivity().application as MyApplication).appComponent.viewModelsFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_bookmark, container, false)
        lifecycleScope.launch {
            movieViewModel.init()
            movieViewModel.fetchAllBookmarkMovies()
        }
        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    activity!!.finish()
                }
            })
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var recyclerViewTest: View = view.findViewById(R.id.noBookmarkLayout)

        if (UserManager.bookmarkList.isNotEmpty()) {
            recyclerView = view.findViewById(R.id.recycler_view_bookmark)
            recyclerView.setHasFixedSize(true)
            recyclerView.layoutManager =
                LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
            recyclerView.adapter =
                MoviesAdapter(UserManager.bookmarkList, listener = { movie, image ->
                    val intent2 = Intent(activity, MovieDetailActivity::class.java)
                    intent2.putExtra(HomeFragment.INTENT_PARCELABLE, movie)
                    intent2.putExtra("Shared Trend Image", ViewCompat.getTransitionName(image));
                    val options: ActivityOptionsCompat =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(
                            requireActivity(), image, ViewCompat.getTransitionName(image)!!
                        )
                    startActivityForResult(intent2, 1, options.toBundle())
                }, onBookmarkClick = { movie ->
                    if (UserManager.bookmarkList.isNotEmpty()) {
                        recyclerViewTest.visibility = RecyclerView.GONE
                        UserManager.bookmarkList.remove(movie)
                        UserManager.bookmarkId.remove(movie.movieId)
                        removeBookmark(movie.movieId)
                        movieViewModel.mutableBookmark.observe(viewLifecycleOwner) {
                            adapter.notifyDataSetChanged()
                        }
                    } else {
                        recyclerViewTest.visibility = RecyclerView.VISIBLE;
                        recyclerView.visibility = RecyclerView.INVISIBLE
                    }
                }, 3)
            recyclerViewTest.visibility = View.GONE
            adapter = recyclerView.adapter as MoviesAdapter
            movieViewModel.allBookmarkMoviesLiveData.observe(viewLifecycleOwner) {
                adapter.updateData(UserManager.bookmarkList)
            }
        } else {
            recyclerViewTest.visibility = View.VISIBLE
        }
    }

    private fun removeBookmark(movieId: Int) {
        UserManager.userObj?.let { movieViewModel.removeBookmark(it.userId, movieId) }
    }
}
