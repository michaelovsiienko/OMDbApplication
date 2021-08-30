package com.testapp.omdbtestapplication.ui.movie_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.testapp.omdbtestapplication.data.OmdbMovieDetailResponse
import com.testapp.omdbtestapplication.repository.OmdbRepository
import com.testapp.omdbtestapplication.data.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val repository: OmdbRepository
) : ViewModel() {

    private var _isLoadingLiveData: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoadingLiveData: LiveData<Boolean> get() = _isLoadingLiveData

    private var _movieDetailLiveData: MutableLiveData<OmdbMovieDetailResponse> = MutableLiveData()
    val movieDetailLiveData: LiveData<OmdbMovieDetailResponse> = _movieDetailLiveData

    private var _errorLiveData: MutableLiveData<String> = MutableLiveData()
    val errorLiveData: LiveData<String> get() = _errorLiveData

    fun getMovieDetails(movieId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoadingLiveData.postValue(true)
            when (val response = repository.getMovieDetails(movieId)) {
                is Resource.Error -> {
                    _errorLiveData.postValue(response.message)
                    _isLoadingLiveData.postValue(false)
                }
                is Resource.Success -> {
                    val movies = response.data
                    _movieDetailLiveData.postValue(movies)
                    _isLoadingLiveData.postValue(false)
                }
            }
        }
    }

}