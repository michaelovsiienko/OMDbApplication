package com.testapp.omdbtestapplication.data

import com.google.gson.annotations.SerializedName

data class OmdbMovieBaseResponse(
    @SerializedName("Search") val movies: List<OmdbMovieResponse>,
    @SerializedName("totalResults") val totalResults: String,
    @SerializedName("Response") val response: String,
    @SerializedName("Error") val error: String
)