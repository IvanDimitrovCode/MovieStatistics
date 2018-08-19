package com.example.ivan.moviestatistics.movie

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    var title: String,
    var year: Int,
    var runtime: Int,
    var tagline: String,
    var overview: String,
    var genres: List<String>) : Parcelable