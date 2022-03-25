package com.mobileoak.sampleproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobileoak.sampleproject.model.Movie
import com.mobileoak.sampleproject.model.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class MainState {
    object Empty: MainState()
    data class Error(val message: String): MainState()
    data class DataLoaded(val data: List<Movie>): MainState()
}

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: MovieRepository) : ViewModel() {
    private val TAG = MainViewModel::class.simpleName
    private val _state = MutableLiveData<MainState>().apply {
        value = MainState.Empty
    }

    val state : LiveData<MainState>
        get() = _state

    init {
        viewModelScope.launch {
            try {
                val movieList = repository.getMovies(1)
                _state.postValue(MainState.DataLoaded(movieList))
            } catch (exception: Exception) {
                _state.postValue(MainState.Error("An error occurred: " + exception.message))
            }
        }

    }
}