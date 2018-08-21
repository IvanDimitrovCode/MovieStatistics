package com.example.ivan.moviestatistics.movie

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.ItemKeyedDataSource
import android.provider.ContactsContract
import com.example.ivan.moviestatistics.movie.api.ApiInterface
import com.example.ivan.moviestatistics.movie.models.Movie
import io.reactivex.disposables.CompositeDisposable

class MovieDataSource(private val moveApi: ApiInterface,
                      private val compositeDisposable: CompositeDisposable)
    : ItemKeyedDataSource<String, Movie>() {
    private val movieType = "movie"

    private var pageNumber = 1
    private var query: String = ""
    val loadState = MutableLiveData<DataLoadState>()

    override fun loadInitial(params: LoadInitialParams<String>, callback: LoadInitialCallback<Movie>) {
        loadState.postValue(DataLoadState.LOADING)
        query = params.requestedInitialKey.toString()
        requestMovieList(params.requestedLoadSize, callback)
    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<Movie>) {
        loadState.postValue(DataLoadState.LOADING)
        requestMovieList(params.requestedLoadSize, callback)
    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<Movie>) {
    }

    override fun getKey(item: Movie): String = item.title

    private fun requestMovieList(requestedLoadSize: Int, callback: LoadCallback<Movie>) {
        loadState.postValue(DataLoadState.LOADING)
        if (query == "null") {
            requestTopMovies(requestedLoadSize, callback)
        } else {
            requestMovieForQuery(requestedLoadSize, callback)
        }
    }

    private fun requestTopMovies(requestedLoadSize: Int, callback: LoadCallback<Movie>) {
        compositeDisposable.add(moveApi.getMovies(pageNumber++, requestedLoadSize).subscribe { movies ->
            loadState.postValue(DataLoadState.LOADED)
            callback.onResult(movies)
        })
    }

    private fun requestMovieForQuery(requestedLoadSize: Int, callback: LoadCallback<Movie>) {
        compositeDisposable.add(
                moveApi.getMoviesForQuery(movieType, query, pageNumber++,
                        requestedLoadSize).subscribe({ movies ->
                    loadState.postValue(DataLoadState.LOADED)
                    val transformer = movies.map { generalMovieData -> generalMovieData.movie }.toList()
                    callback.onResult(transformer)
                }, {
                    loadState.postValue(DataLoadState.FAILED)
                }))
    }

}