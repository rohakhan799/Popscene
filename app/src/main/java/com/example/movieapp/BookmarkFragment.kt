package com.example.movieapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.adapter.MoviesAdapter
import com.example.movieapp.data.Datasource
import com.example.movieapp.model.UserManager

class BookmarkFragment : Fragment() {
    lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_bookmark, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var recyclerViewTest: View = view.findViewById(R.id.noBookmarkLayout)

        if (UserManager.bookmarkList.isNotEmpty()) {
            val myDataset = Datasource().loadMovieList()
            recyclerView = view.findViewById(R.id.recycler_view_bookmark)
            recyclerView.setHasFixedSize(true)

            recyclerView.layoutManager =
                LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
            recyclerView.adapter = MoviesAdapter(
                UserManager.bookmarkList,
                listener = { movie, image ->
                    val intent2: Intent = Intent(activity, MovieDetailActivity::class.java)
                    intent2.putExtra(HomeFragment.INTENT_PARCELABLE, movie)
                    intent2.putExtra("Shared Trend Image", ViewCompat.getTransitionName(image));
                    val options: ActivityOptionsCompat =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(
                            requireActivity(),
                            image,
                            ViewCompat.getTransitionName(image)!!
                        )
                    startActivityForResult(intent2, 1, options.toBundle())
                },
                onBookmarkClick = {
                    //TODO: Check if list is empty and show view
                    if (UserManager.bookmarkList.isNotEmpty()) {
                        recyclerViewTest.visibility = RecyclerView.GONE
                      //  recyclerView.visibility= RecyclerView.VISIBLE

                    } else {
                        recyclerViewTest.visibility = RecyclerView.VISIBLE;
                        recyclerView.visibility=RecyclerView.INVISIBLE

                    }
                }, 3
            )
            recyclerViewTest.visibility = View.GONE
        } else {
            recyclerViewTest.visibility = View.VISIBLE
        }
    }
}
