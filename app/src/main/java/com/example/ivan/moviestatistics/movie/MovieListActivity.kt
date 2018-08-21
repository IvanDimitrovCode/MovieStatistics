package com.example.ivan.moviestatistics.movie

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.arch.paging.PagedList
import android.content.res.Configuration
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.ivan.moviestatistics.R
import kotlinx.android.synthetic.main.activity_main.*
import android.support.v7.widget.SearchView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.Menu
import com.example.ivan.moviestatistics.movie.models.Movie


class MovieListActivity : AppCompatActivity(), SearchView.OnQueryTextListener {
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.search_menu, menu)
        val searchMenuItem = menu.findItem(R.id.menu_toolbarsearch)
        val searchView = searchMenuItem.actionView as SearchView
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(this)
        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        startQuerySearchObservation(query)
        return true
    }

    private fun startQuerySearchObservation(query: String?) {
        if (query != null) {
            movieViewModel?.getMoviesForQuery(query)?.observe(this, Observer {
                updateListStatus(it)
            })
        }
    }

    private fun startListDataObservation() {
        movieViewModel?.getMovies()?.observe(this, Observer {
            updateListStatus(it)
        })
    }

    private fun startDataStatusObservation() {
        movieViewModel?.dataLoadStatus()?.observe(this, Observer {
            when (it) {
                DataLoadState.LOADING -> showLoadingIndicator()
                DataLoadState.LOADED -> hideLoadingIndicator()
                DataLoadState.FAILED -> hideLoadingIndicator()
                else -> {
                    throw Exception("Unhandled state")
                }
            }
        })
    }

    private fun setupAdapter() {
        adapter = MovieListAdapter()
        recyclerview.adapter = adapter
    }

    private fun setupGridLayout() {
        val orientation = resources.configuration.orientation

        val gridLayoutManager = if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.HORIZONTAL)
        } else {
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        }

        recyclerview.layoutManager = gridLayoutManager
    }

    private fun updateListStatus(it: PagedList<Movie>?) {
        if (it != null && it.isEmpty()) showNoResultsMessage()
        else hideNoResultsMessage()
        adapter.submitList(it)
    }

    private fun showLoadingIndicator() {
        loadingIndicator.visibility = View.VISIBLE
    }

    private fun hideLoadingIndicator() {
        loadingIndicator.visibility = View.INVISIBLE
    }

    private fun showNoResultsMessage() {
        noResultsMessage.visibility = View.VISIBLE
    }

    private fun hideNoResultsMessage() {
        noResultsMessage.visibility = View.INVISIBLE
    }

}
