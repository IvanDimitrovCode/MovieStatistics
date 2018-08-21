package com.example.ivan.moviestatistics.movie

import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton
import com.example.ivan.moviestatistics.R
import com.example.ivan.moviestatistics.movie.models.Movie
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.content_movie.view.*

class MovieListAdapter :
        PagedListAdapter<Movie, MovieListAdapter.ViewHolder>(MovieDiffCallback) {

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val movieTitleView: TextView = view.movieTitle
        val movieOverviewView: TextView = view.movieOverview
        val movieYearView: TextView = view.movieYear
        val expandImageView: ToggleButton = view.imageView
        val poster: ImageView = view.ivImage
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.content_movie, parent, false) as View
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movieItem = getItem(position)
        Picasso.get().load(movieItem?.let { composeUrlForMovie(it) }).into(holder.poster)
        holder.movieTitleView.text = movieItem?.title
        holder.movieOverviewView.text = movieItem?.overview
        holder.movieYearView.text = movieItem?.year.toString()
        setupOverviewExpanding(holder)
    }

    private fun setupOverviewExpanding(holder: ViewHolder) {
        holder.expandImageView.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) holder.movieOverviewView.maxLines = Integer.MAX_VALUE
            else holder.movieOverviewView.maxLines = 1
        }
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
