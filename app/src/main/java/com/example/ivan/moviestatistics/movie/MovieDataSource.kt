package com.example.ivan.moviestatistics.movie

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.ItemKeyedDataSource
import com.example.ivan.moviestatistics.movie.DataLoadState
import com.example.ivan.moviestatistics.movie.Movie
import com.example.ivan.moviestatistics.movie.api.ApiInterface
import io.reactivex.disposables.CompositeDisposable

class MovieDataSource(private val moveApi: ApiInterface, private val compositeDisposable: CompositeDisposable) : ItemKeyedDataSource<String, Movie>() {
    private var pageNumber = 0
    val loadState = MutableLiveData<DataLoadState>()

    override fun loadInitial(params: LoadInitialParams<String>, callback: LoadInitialCallback<Movie>) {
        loadState.postValue(DataLoadState.LOADING)
        compositeDisposable.add(moveApi.getMovies(++pageNumber, params.requestedLoadSize).subscribe { users ->
            loadState.postValue(DataLoadState.LOADED)
            callback.onResult(users)
        })
    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<Movie>) {
        loadState.postValue(DataLoadState.LOADING)
        compositeDisposable.add(moveApi.getMovies(++pageNumber, params.requestedLoadSize).subscribe { users ->
            loadState.postValue(DataLoadState.LOADED)
            callback.onResult(users)
        })
    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<Movie>) {
    }

    override fun getKey(item: Movie): String = item.title


}