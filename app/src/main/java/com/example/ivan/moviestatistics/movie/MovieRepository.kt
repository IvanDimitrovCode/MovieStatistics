package com.example.ivan.moviestatistics.movie

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.example.ivan.moviestatistics.movie.api.ApiInterface
import com.example.ivan.moviestatistics.movie.models.Movie
import io.reactivex.disposables.CompositeDisposable

class MovieRepository(movieService: ApiInterface
                      , compositeDisposable: CompositeDisposable) {

    private val movieDataSourceFactory = MovieDataSourceFactory(compositeDisposable, movieService)

    fun getMovies(): LiveData<PagedList<Movie>> {
        val config = getPagingConfigurations()
        return LivePagedListBuilder(movieDataSourceFactory, config)
                .build()
    }

    fun getMoviesForQuery(query: String): LiveData<PagedList<Movie>> {
        val config = getPagingConfigurations()
        return LivePagedListBuilder(movieDataSourceFactory, config)
                .setInitialLoadKey(query)
                .build()
    }

    fun getDataLoadStatus(): LiveData<DataLoadState> {
        return Transformations.switchMap(movieDataSourceFactory.dataSourceLiveData,
                { dataSource -> dataSource.loadState })
    }

    private fun getPagingConfigurations(): PagedList.Config =
            PagedList.Config.Builder()
                    .setInitialLoadSizeHint(10)
                    .setPageSize(10)
                    .build()

}