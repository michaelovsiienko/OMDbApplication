package com.testapp.omdbtestapplication.ui.search_movie.adapter

interface SearchMovieAdapterCallback {

    fun onMovieClicked(movieId: String)

    fun onWatchLaterClicked(movieId: String, isAddedToWatchLater: Boolean)

    fun onWatchedClicked(movieId: String, isAddedToWatched: Boolean)
}