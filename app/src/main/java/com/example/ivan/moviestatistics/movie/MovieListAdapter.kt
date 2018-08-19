package com.example.ivan.moviestatistics.movie

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.ivan.moviestatistics.R
import kotlinx.android.synthetic.main.content_movie.view.*

class MovieListAdapter(private val movieList: Array<Movie>,
                       private val onItemClickListener: OnItemClickListener) :
        RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(movie: Movie)
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val movieTitleView: TextView = view.movieTitle
        val movieOverviewView: TextView = view.movieOverview
        val movieYearView: TextView = view.movieYear
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.content_movie, parent, false) as View
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movieItem = movieList[position]
        holder.movieTitleView.text = movieItem.title
        holder.movieOverviewView.text = movieItem.overview
        holder.movieYearView.text = movieItem.year.toString()
        holder.view.setOnClickListener { onItemClickListener.onItemClick(movieList[position]) }
    }

    override fun getItemCount() = movieList.size
}
