package com.example.ivan.moviestatistics.movie

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.arch.paging.PagedList
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.ivan.moviestatistics.R
import kotlinx.android.synthetic.main.activity_main.*
import android.support.v7.widget.SearchView
import android.view.Menu
import com.example.ivan.moviestatistics.movie.models.Movie
import android.widget.Toast
import com.example.ivan.moviestatistics.movie.utils.getGridLayout
import kotlinx.android.synthetic.main.content_movie.*
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth
import android.view.Display


class MovieListActivity : AppCompatActivity(), SearchView.OnQueryTextListener {
    private var movieViewModel: MovieViewModel? = null
    private lateinit var adapter: MovieListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        removeActionBarTitle()
        setupGridLayout()
        setupAdapter()
        setupSwipeToRefresh()
        if (isNetworkAvailable()) {
            setupDataObservation()
        } else {
            showNoNetworkToast()
        }
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

    override fun onQueryTextSubmit(query: String): Boolean {
        return true
    }

    override fun onQueryTextChange(query: String): Boolean {
        if (isNetworkAvailable()) movieViewModel?.getMoviesForQuery(query)
        else showNoNetworkToast()
        return true
    }

    private fun removeActionBarTitle() {
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayShowHomeEnabled(false)
    }

    private fun setupDataObservation() {
        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel::class.java)
        startDataStatusObservation()
        startListDataObservation()
    }

    private fun startListDataObservation() {
        movieViewModel?.movieData?.observe(this, Observer {
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

    private fun setupSwipeToRefresh() {
        swiperefresh.setOnRefreshListener {
            if (isNetworkAvailable()) {
                movieViewModel?.refresh()
            } else {
                hideLoadingIndicator()
                showNoNetworkToast()
            }
        }
    }

    private fun showNoNetworkToast() {
        Toast.makeText(this, resources.getString(R.string.no_network_message),
                Toast.LENGTH_LONG).show()
    }

    private fun setupAdapter() {
        adapter = MovieListAdapter()
        recyclerview.adapter = adapter
    }

    private fun setupGridLayout() {
        val gridLayoutManager = getGridLayout(windowManager.defaultDisplay, resources.getDimension(R.dimen.card_view_width).toInt())
        recyclerview.layoutManager = gridLayoutManager
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    private fun updateListStatus(it: PagedList<Movie>?) {
        if (it != null && it.isEmpty()) showNoResultsMessage()
        else hideNoResultsMessage()
        adapter.submitList(it)
    }

    private fun showLoadingIndicator() {
        swiperefresh.isRefreshing = true
    }

    private fun hideLoadingIndicator() {
        swiperefresh.isRefreshing = false
    }

    private fun showNoResultsMessage() {
        noResultsMessage.visibility = View.VISIBLE
    }

    private fun hideNoResultsMessage() {
        noResultsMessage.visibility = View.INVISIBLE
    }

}
