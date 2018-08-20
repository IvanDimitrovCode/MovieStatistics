package com.example.ivan.moviestatistics.movie

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.res.Configuration
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.example.ivan.moviestatistics.R
import kotlinx.android.synthetic.main.activity_main.*
import android.support.v7.widget.SearchView
import android.view.Menu
import com.example.ivan.moviestatistics.movie.models.Movie


class MovieListActivity : AppCompatActivity(), MovieListAdapter.OnItemClickListener, SearchView.OnQueryTextListener {
    private var movieViewModel: MovieViewModel? = null
    private lateinit var adapter: MovieListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupGridLayout()
        setupAdapter()
        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel::class.java)
        startDataStatusObservation()
        startListDataObservation()

    }

    private fun setupAdapter() {
        adapter = MovieListAdapter(this)
        recyclerview.adapter = adapter
    }

    private fun startListDataObservation() {
        movieViewModel?.getMovies()?.observe(this, Observer {
            adapter.submitList(it)
        })
    }

    private fun startDataStatusObservation() {
        movieViewModel?.dataLoadStatus()?.observe(this, Observer {
            when (it) {
                DataLoadState.LOADING -> showLoadingIndicator()
                DataLoadState.LOADED -> hideLoadingIndicator()
                else -> {
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.search_menu, menu)
        val searchMenuItem = menu.findItem(R.id.menu_toolbarsearch)
        val searchView = searchMenuItem.actionView as SearchView
        searchView.queryHint = "search movie"
        searchView.setOnQueryTextListener(this)
        return true
    }

    private fun setupGridLayout() {
        val orientation = resources.configuration.orientation

        val gridLayoutManager = if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            GridLayoutManager(this@MovieListActivity, 2)
        } else {
            GridLayoutManager(this@MovieListActivity, 1)
        }

        recyclerview.layoutManager = gridLayoutManager
    }

    private fun showLoadingIndicator() {
        loadingIndicator.visibility = View.VISIBLE
    }

    private fun hideLoadingIndicator() {
        loadingIndicator.visibility = View.INVISIBLE
    }

    override fun onQueryTextSubmit(p0: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(p0: String?): Boolean {
        if (p0 != null) {
            movieViewModel?.getMoviesForQuery(p0)?.observe(this, Observer {
                adapter.submitList(it)
            })
        }
        return true
    }

    override fun onItemClick(movie: Movie) {
    }
}
