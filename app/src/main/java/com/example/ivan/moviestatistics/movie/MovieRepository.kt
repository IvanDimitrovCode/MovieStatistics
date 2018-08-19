package com.example.ivan.moviestatistics.movie

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.example.ivan.moviestatistics.movie.Movie
import com.example.ivan.moviestatistics.movie.MovieDataSourceFactory
import com.example.ivan.moviestatistics.movie.api.ApiInterface
import io.reactivex.disposables.CompositeDisposable

class MovieRepository(movieService: ApiInterface
                      , compositeDisposable: CompositeDisposable) {

    private val movieDataSourceFactory = MovieDataSourceFactory(compositeDisposable, movieService)
    private var movies: LiveData<PagedList<Movie>>? = null

    fun getMovies(): LiveData<PagedList<Movie>> {
        val config = PagedList.Config.Builder()
            .setInitialLoadSizeHint(10)
            .setPageSize(10)
            .build()

        movies = LivePagedListBuilder(movieDataSourceFactory, config)
            .setInitialLoadKey("")
            .build()

        return movies as LiveData<PagedList<Movie>>
    }

    public fun getDataLoadStatus(): LiveData<DataLoadState> {
        return Transformations.switchMap(movieDataSourceFactory.dataSourceLiveData
        ) { dataSource -> dataSource.loadState }
    }
}