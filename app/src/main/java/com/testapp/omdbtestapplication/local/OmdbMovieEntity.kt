package com.testapp.omdbtestapplication.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_table")
data class OmdbMovieEntity(
    @PrimaryKey
    var imdbId: String,
    var isAddedToWatchLater: Boolean = false,
    var isAddedToWatched: Boolean = false
)