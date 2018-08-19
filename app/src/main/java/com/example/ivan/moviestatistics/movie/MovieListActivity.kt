package com.example.ivan.moviestatistics.movie

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.res.Configuration
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.View
import com.example.ivan.moviestatistics.R
import kotlinx.android.synthetic.main.activity_main.*


class MovieListActivity : AppCompatActivity(), MovieListAdapter.OnItemClickListener {
    private var movieViewModel: MovieViewModel? = null
    private lateinit var adapter: MovieListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val orientation = resources.configuration.orientation
        val gridLayoutManager = requestGridLayoutManager(orientation)
        recyclerview.layoutManager = gridLayoutManager
        adapter = MovieListAdapter(this)
        recyclerview.adapter = adapter
        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel::class.java)
        movieViewModel!!.dataLoadStatus().observe(this, Observer {
            when (it) {
                DataLoadState.LOADING -> showLoadingIndicator()
                DataLoadState.LOADED -> hideLoadingIndicator()
                else -> {
                }
            }
        })
        movieViewModel!!.getMovies().observe(this, Observer {
            adapter.submitList(it)
        })
    }

    private fun requestGridLayoutManager(orientation: Int): GridLayoutManager {
        return if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            GridLayoutManager(this@MovieListActivity, 3)
        } else {
            GridLayoutManager(this@MovieListActivity, 2)
        }
    }

    fun showLoadingIndicator() {
        Log.d("DATA_STUFF", "SHOW")
        loadingIndicator.visibility = View.VISIBLE
    }

    fun hideLoadingIndicator() {
        Log.d("DATA_STUFF", "HIDE")
        loadingIndicator.visibility = View.INVISIBLE
    }

    override fun onItemClick(movie: Movie) {
    }

    fun showMovieList(list: List<Movie>) {
    }
}
