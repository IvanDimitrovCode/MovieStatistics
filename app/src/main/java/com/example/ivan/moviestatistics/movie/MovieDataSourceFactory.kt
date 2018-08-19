package com.example.ivan.moviestatistics.movie

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import com.example.ivan.moviestatistics.movie.api.ApiInterface
import com.example.ivan.moviestatistics.movie.models.Movie
import io.reactivex.disposables.CompositeDisposable


class MovieDataSourceFactory(private val compositeDisposable: CompositeDisposable,
                             private val movieService: ApiInterface) : DataSource.Factory<String, Movie>() {

    val dataSourceLiveData = MutableLiveData<MovieDataSource>()

    override fun create(): DataSource<String, Movie> {
        val movieDataSource = MovieDataSource(movieService, compositeDisposable)
        dataSourceLiveData.postValue(movieDataSource)
        return movieDataSource
    }

}