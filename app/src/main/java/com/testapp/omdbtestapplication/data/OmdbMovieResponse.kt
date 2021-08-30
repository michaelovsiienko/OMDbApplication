package com.testapp.omdbtestapplication.data

import com.google.gson.annotations.SerializedName

data class OmdbMovieResponse(
    @SerializedName("Title") val title: String,
    @SerializedName("Year") val releaseYear: String,
    @SerializedName("imdbID") val imdbId: String,
    @SerializedName("Type") val type: String,
    @SerializedName("Poster") val poster: String,
    var isAddedToWatchLater: Boolean = false,
    var isAddedToWatched: Boolean = false
)