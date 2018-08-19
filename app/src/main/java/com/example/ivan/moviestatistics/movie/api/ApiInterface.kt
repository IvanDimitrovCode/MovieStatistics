package com.example.ivan.moviestatistics.movie.api

import com.example.ivan.moviestatistics.movie.Movie
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("/movies/popular")
    fun getMovies(@Query("page") pageNo: Int, @Query("limit") limit: Int): Flowable<List<Movie>>
}