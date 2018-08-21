package com.example.ivan.moviestatistics.movie

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.paging.PagedList
import com.example.ivan.moviestatistics.movie.api.ApiClient
import com.example.ivan.moviestatistics.movie.models.Movie
import io.reactivex.disposables.CompositeDisposable

class MovieViewModel(application: Application) : AndroidViewModel(application) {
    private val compositeDisposable = CompositeDisposable()
    private var repository = MovieRepository(ApiClient.getClient()!!, compositeDisposable)
    private var liveData: LiveData<PagedList<Movie>>? = null

    fun getMovies(): LiveData<PagedList<Movie>> {
        compositeDisposable.clear()
        if (liveData == null) {
            liveData = repository.getMovies()
        }
        return liveData!!
    }

    fun getMoviesForQuery(query: String): LiveData<PagedList<Movie>> {
        compositeDisposable.clear()
        liveData = repository.getMoviesForQuery(query)
        return liveData!!
    }

    fun dataLoadStatus(): LiveData<DataLoadState> {
        return repository.getDataLoadStatus()
    }

}