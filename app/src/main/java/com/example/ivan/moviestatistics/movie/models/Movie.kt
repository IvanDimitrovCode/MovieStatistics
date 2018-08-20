package com.example.ivan.moviestatistics.movie.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
        var title: String,
        var type: String,
        var year: Int,
        var ids: MovieIds,
        var runtime: Int,
        var tagline: String,
        var overview: String,
        var genres: List<String>) : Parcelable