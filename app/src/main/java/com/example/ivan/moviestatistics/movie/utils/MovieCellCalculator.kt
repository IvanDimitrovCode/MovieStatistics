package com.example.ivan.moviestatistics.movie.utils

import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.DisplayMetrics
import android.view.Display

fun getGridLayout(display: Display, cardViewWidth: Int): StaggeredGridLayoutManager {
    val displayMetrics = DisplayMetrics()
    display.getMetrics(displayMetrics)
    val cellsPerRow = calculateCardPerRow(cardViewWidth, displayMetrics.widthPixels)
    return StaggeredGridLayoutManager(cellsPerRow, StaggeredGridLayoutManager.VERTICAL)
}

private fun calculateCardPerRow(cardViewWidth: Int, layoutWidth: Int): Int {
    return layoutWidth / cardViewWidth
}