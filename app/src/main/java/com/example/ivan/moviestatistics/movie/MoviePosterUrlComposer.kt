package com.example.ivan.moviestatistics.movie

import com.example.ivan.moviestatistics.movie.models.Movie

fun composeUrlForMovie(movie: Movie): String {
    return "http://img.omdbapi.com/?apikey=f83167f4&i=${movie.ids.imdb}"
}