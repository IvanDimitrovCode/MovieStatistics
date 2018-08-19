package com.example.ivan.moviestatistics.movie

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.paging.PagedList
import com.example.ivan.moviestatistics.movie.Movie
import com.example.ivan.moviestatistics.movie.MovieRepository
import com.example.ivan.moviestatistics.movie.api.ApiClient
import io.reactivex.disposables.CompositeDisposable

class MovieViewModel(application: Application) : AndroidViewModel(application) {
    private var repository: MovieRepository
    private val compositeDisposable = CompositeDisposable()

    init {
        repository = MovieRepository(ApiClient.getClient()!!, compositeDisposable)
    }

    fun getMovies(): LiveData<PagedList<Movie>> {
        return repository.getMovies()
    }

    fun dataLoadStatus(): LiveData<DataLoadState> {
        return repository.getDataLoadStatus()
    }

}