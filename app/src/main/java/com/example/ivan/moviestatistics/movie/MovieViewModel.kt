package com.example.ivan.moviestatistics.movie

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.example.ivan.moviestatistics.movie.api.ApiClient
import com.example.ivan.moviestatistics.movie.models.Movie
import io.reactivex.disposables.CompositeDisposable

class MovieViewModel(application: Application) : AndroidViewModel(application) {
    private val compositeDisposable = CompositeDisposable()
    var movieData: LiveData<PagedList<Movie>>? = null
    private val sourceFactory: MovieDataSourceFactory

    init {
        sourceFactory = MovieDataSourceFactory(compositeDisposable, ApiClient.getClient()!!)

        val config = PagedList.Config.Builder()
                .setPageSize(10)
                .setInitialLoadSizeHint(10)
                .build()
        movieData = LivePagedListBuilder(sourceFactory, config).build()
    }

    fun refresh() {
        sourceFactory.dataSourceLiveData.value!!.invalidate()
    }

    fun getMoviesForQuery(query: String) {
        sourceFactory.query = query
        sourceFactory.dataSourceLiveData.value!!.invalidate()
    }

    fun dataLoadStatus(): LiveData<DataLoadState> {
        return Transformations.switchMap(sourceFactory.dataSourceLiveData,
                { dataSource -> dataSource.loadState })
    }

}