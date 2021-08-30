package com.testapp.omdbtestapplication.ui.search_movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.testapp.omdbtestapplication.data.OmdbMovieResponse
import com.testapp.omdbtestapplication.repository.OmdbRepository
import com.testapp.omdbtestapplication.data.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchMovieViewModel @Inject constructor(
    private val repository: OmdbRepository
) : ViewModel() {

    private var _isLoadingLiveData: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoadingLiveData: LiveData<Boolean> get() = _isLoadingLiveData

    private var _moviesLiveData: MutableLiveData<List<OmdbMovieResponse>> = MutableLiveData()
    val moviesLiveData: LiveData<List<OmdbMovieResponse>> = _moviesLiveData

    private var _errorLiveData: MutableLiveData<String> = MutableLiveData()
    val errorLiveData: LiveData<String> get() = _errorLiveData

    fun search(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoadingLiveData.postValue(true)
            when (val response = repository.searchMovie(query)) {
                is Resource.Error -> {
                    _errorLiveData.postValue(response.message)
                    _isLoadingLiveData.postValue(false)
                }
                is Resource.Success -> {
                    _moviesLiveData.postValue(response.data)
                    _isLoadingLiveData.postValue(false)
                }
            }
        }
    }

    fun updateWatchLater(movieId: String, isAddedToWatchLater: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateWatchLater(movieId, isAddedToWatchLater)
        }
    }

    fun updateWatched(movieId: String, isAddedToWatched: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateWatched(movieId, isAddedToWatched)
        }
    }

}