package com.testapp.omdbtestapplication.repository

import com.testapp.omdbtestapplication.data.OmdbMovieDetailResponse
import com.testapp.omdbtestapplication.data.OmdbMovieResponse
import com.testapp.omdbtestapplication.local.OmdbDao
import com.testapp.omdbtestapplication.local.OmdbMovieEntity
import com.testapp.omdbtestapplication.network.OmdbClient
import com.testapp.omdbtestapplication.data.Resource
import retrofit2.Response
import javax.inject.Inject

class OmdbRepository @Inject constructor(
    private val omdbClient: OmdbClient,
    private val omdbDao: OmdbDao
) {

    suspend fun searchMovie(searchTitle: String): Resource<List<OmdbMovieResponse>> {
        val response = call { omdbClient.searchMovie(searchTitle) }

        return response.data?.movies?.takeIf { it.isNotEmpty() }?.let { remoteMovies ->
            val localMovies = getAllSavedMovies()
            Resource.Success(filterRemoteMovies(remoteMovies, localMovies))
        } ?: Resource.Error(response.data?.error.toString())
    }

    suspend fun getMovieDetails(movieId: String): Resource<OmdbMovieDetailResponse> {
        val response = call { omdbClient.getMovieDetails(movieId) }

        return response.data?.let { details ->
            Resource.Success(details)
        } ?: Resource.Error(response.message.toString())
    }

    suspend fun updateWatchLater(movieId: String, isAddedToWatchLater: Boolean) =
        omdbDao.updateWatchLater(movieId, isAddedToWatchLater)

    suspend fun updateWatched(movieId: String, isAddedToWatched: Boolean) =
        omdbDao.updateWatched(movieId, isAddedToWatched)

    private suspend fun getAllSavedMovies() = omdbDao.getOmdbMovies()

    private fun filterRemoteMovies(
        remoteMovies: List<OmdbMovieResponse>,
        localMovies: List<OmdbMovieEntity>
    ): List<OmdbMovieResponse> {
        remoteMovies.forEach { movie ->
            localMovies.firstOrNull { it.imdbId == movie.imdbId }?.let { localMovie ->
                movie.isAddedToWatchLater = localMovie.isAddedToWatchLater
                movie.isAddedToWatched = localMovie.isAddedToWatched
            }
        }
        return remoteMovies
    }

    private suspend fun <T> call(call: suspend () -> Response<T>): Resource<T?> {
        return try {
            val response = call.invoke()
            return if (response.isSuccessful) {
                Resource.Success(response.body())
            } else {
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            Resource.Error("Network call has failed for a following reason: ${e.message.toString()}")
        }
    }
}