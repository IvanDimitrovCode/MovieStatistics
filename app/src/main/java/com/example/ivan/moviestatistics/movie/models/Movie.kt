package com.example.ivan.moviestatistics.movie.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
        @SerializedName("title") var title: String,
        @SerializedName("type") var type: String,
        @SerializedName("year") var year: Int,
        @SerializedName("runtime") var runtime: Int,
        @SerializedName("tagline") var tagline: String,
        @SerializedName("overview") var overview: String,
        @SerializedName("genres") var genres: List<String>) : Parcelable