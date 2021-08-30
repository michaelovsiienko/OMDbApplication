package com.testapp.omdbtestapplication.data

import com.google.gson.annotations.SerializedName

data class OmdbMovieDetailResponse(
    @SerializedName("Title") val title: String,
    @SerializedName("Year") val releaseYear: String,
    @SerializedName("Rated") val ratingSystem: String,
    @SerializedName("Released") val releaseDate: String,
    @SerializedName("Runtime") val runtime: String,
    @SerializedName("Genre") val genre: String,
    @SerializedName("Director") val director: String,
    @SerializedName("Writer") val writer: String,
    @SerializedName("Actors") val actors: String,
    @SerializedName("Plot") val plot: String,
    @SerializedName("Language") val language: String,
    @SerializedName("Country") val country: String,
    @SerializedName("Awards") val awards: String,
    @SerializedName("Poster") val poster: String,
    @SerializedName("Metascore") val metascore: String,
    @SerializedName("imdbRating") val imdbRating: String,
    @SerializedName("imdbVotes") val imdbVotes: String,
    @SerializedName("imdbID") val imdbId: String,
    @SerializedName("Type") val type: String,
    @SerializedName("BoxOffice") val boxOffice: String,
    @SerializedName("Production") val production: String,
    @SerializedName("Response") val response: String
)