package com.example.ivan.moviestatistics.movie.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GeneralMovieData(
        var type: String,
        var score: Double,
        var movie: Movie) : Parcelable