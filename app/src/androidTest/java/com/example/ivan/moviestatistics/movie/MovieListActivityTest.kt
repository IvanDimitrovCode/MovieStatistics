package com.example.ivan.moviestatistics.movie

import android.content.pm.ActivityInfo
import android.support.test.espresso.assertion.ViewAssertions.*
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.runner.AndroidJUnit4
import com.example.ivan.moviestatistics.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import android.support.v7.widget.RecyclerView
import org.junit.Assert.*
import android.support.test.espresso.action.ViewActions.typeText
import android.widget.AutoCompleteTextView
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import org.hamcrest.core.AllOf


@RunWith(AndroidJUnit4::class)
class MovieListActivityTest {

    @Rule
    @JvmField
    val moviesActivityRule = IntentsTestRule(MovieListActivity::class.java)

    @Test
    fun testStartingOfActivity() {
        Thread.sleep(1000)
        onView(withId(R.id.loadingIndicator)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)))
        assertTrue(getMovieCount() > 0)
    }

    @Test
    fun testPagination() {
        Thread.sleep(1000)
        val countBeforeScroll = getMovieCount()
        scrollToBottom()
        Thread.sleep(1000)
        val countAfterScroll = getMovieCount()
        assertTrue(countAfterScroll > countBeforeScroll)
    }

    @Test
    fun testRotationSave() {
        Thread.sleep(1000)
        scrollToBottom()
        Thread.sleep(1000)
        val countBeforeRotation = getMovieCount()
        rotateDeviceLandscape()
        Thread.sleep(1000)
        val countAfterRotation = getMovieCount()
        assertTrue(countAfterRotation == countBeforeRotation)
    }

    @Test
    fun testCorrectSearch() {
        val searchedMovie = "happy feet"
        typeInSearchBar(searchedMovie)
        Thread.sleep(1000)
        verifyRecyclerViewContainsElement(searchedMovie)
    }

    @Test
    fun testIncorrectSearch() {
        val searchedMovie = "testtestetesttesttest"
        typeInSearchBar(searchedMovie)
        Thread.sleep(1000)
        assertTrue(getMovieCount() == 0)
    }

    @Test
    fun testPaginationForSearch() {
        typeInSearchBar("g")
        Thread.sleep(1000)
        val countBeforeScroll = getMovieCount()
        scrollToBottom()
        Thread.sleep(1000)
        val countAfterScroll = getMovieCount()
        assertTrue(countAfterScroll > countBeforeScroll)
    }

    private fun verifyRecyclerViewContainsElement(searchedMovie: String) {
        onView(AllOf.allOf(withText(searchedMovie), withParent(withId(R.id.recyclerview))))
    }

    private fun typeInSearchBar(searchText: String) {
        onView(withId(R.id.menu_toolbarsearch)).perform(click())
        onView(isAssignableFrom(AutoCompleteTextView::class.java)).perform(typeText(searchText))
    }

    private fun getMovieCount(): Int {
        val recyclerView = moviesActivityRule.activity.findViewById(R.id.recyclerview) as RecyclerView
        return recyclerView.adapter!!.itemCount
    }

    private fun rotateDeviceLandscape() {
        moviesActivityRule.activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    }

    private fun scrollToBottom() {
        val recyclerView = moviesActivityRule.activity.findViewById(R.id.recyclerview) as RecyclerView
        val position = recyclerView.adapter?.itemCount!! - 1

        onView(withId(R.id.recyclerview))
                .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(position))
    }
}