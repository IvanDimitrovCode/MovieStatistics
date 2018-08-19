package com.example.ivan.moviestatistics.movie.api

import com.example.ivan.moviestatistics.movie.models.GeneralMovieData
import com.example.ivan.moviestatistics.movie.models.Movie
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {
    @GET("/movies/popular")
    fun getMovies(@Query("page") pageNo: Int,
                  @Query("limit") limit: Int): Flowable<List<Movie>>

    @GET("/search/{id_type}")
    fun getMoviesForQuery(@Path("id_type") type: String,
                        @Query("query") query: String,
                        @Query("page") pageNo: Int?,
                        @Query("limit") limit: Int?): Flowable<List<GeneralMovieData>>
}