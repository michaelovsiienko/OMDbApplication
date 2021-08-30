package com.testapp.omdbtestapplication.local

import androidx.room.*

@Dao
interface OmdbDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOmdbMovie(omdbMovie: OmdbMovieEntity)

    @Query("SELECT * FROM movie_table")
    suspend fun getOmdbMovies(): List<OmdbMovieEntity>

    @Query("SELECT EXISTS(SELECT * FROM movie_table WHERE imdbId = :movieId)")
    fun isRowExists(movieId: String): Boolean

    @Query("UPDATE movie_table SET isAddedToWatchLater = :isAddedToWatchLater WHERE imdbId = :movieId")
    suspend fun updateWatchLaterField(movieId: String, isAddedToWatchLater: Boolean)

    @Query("UPDATE movie_table SET isAddedToWatched = :isAddedToWatched WHERE imdbId = :movieId")
    suspend fun updateWatchedField(movieId: String, isAddedToWatched: Boolean)

    @Transaction
    suspend fun updateWatchLater(movieId: String, isAddedToWatchLater: Boolean) {
        if (isRowExists(movieId)) {
            updateWatchLaterField(movieId, isAddedToWatchLater)
        } else {
            insertOmdbMovie(OmdbMovieEntity(movieId, isAddedToWatchLater = isAddedToWatchLater))
        }
    }

    @Transaction
    suspend fun updateWatched(movieId: String, isAddedToWatched: Boolean) {
        if (isRowExists(movieId)) {
            updateWatchedField(movieId, isAddedToWatched)
        } else {
            insertOmdbMovie(OmdbMovieEntity(movieId, isAddedToWatched = isAddedToWatched))
        }
    }
}