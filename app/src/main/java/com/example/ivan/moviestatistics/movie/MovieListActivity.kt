package com.example.ivan.moviestatistics.movie

import android.content.res.Configuration
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import com.example.ivan.moviestatistics.R
import kotlinx.android.synthetic.main.activity_main.*


class MovieListActivity : AppCompatActivity(), MovieListAdapter.OnItemClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val orientation = resources.configuration.orientation
        val gridLayoutManager = requestGridLayoutManager(orientation)
        recyclerview.layoutManager = gridLayoutManager
    }

    private fun requestGridLayoutManager(orientation: Int): GridLayoutManager {
        return if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            GridLayoutManager(this@MovieListActivity, 3)
        } else {
            GridLayoutManager(this@MovieListActivity, 2)
        }
    }

    override fun onItemClick(movie: Movie) {
    }
}
