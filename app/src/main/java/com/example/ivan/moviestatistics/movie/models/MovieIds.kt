package com.example.ivan.moviestatistics.movie.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieIds(var imdb: String) : Parcelable