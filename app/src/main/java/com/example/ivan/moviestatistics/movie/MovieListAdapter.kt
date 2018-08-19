package com.example.ivan.moviestatistics.movie

import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.ivan.moviestatistics.R
import kotlinx.android.synthetic.main.content_movie.view.*

class MovieListAdapter(private val onItemClickListener: OnItemClickListener) :
    PagedListAdapter<Movie, MovieListAdapter.ViewHolder>(MovieDiffCallback) {

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
        val movieItem = getItem(position)
        holder.movieTitleView.text = movieItem?.title
        holder.movieOverviewView.text = movieItem?.overview
        holder.movieYearView.text = movieItem?.year.toString()
//        holder.view.setOnClickListener { onItemClickListener.onItemClick(movieList[position]) }
    }

    companion object {
        val MovieDiffCallback = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }
        }
    }
}
