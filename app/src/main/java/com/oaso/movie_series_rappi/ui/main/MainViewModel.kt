package com.oaso.movie_series_rappi.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oaso.movie_series_rappi.model.Movie
import com.oaso.movie_series_rappi.model.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository
) : ViewModel() {

    private val _model = MutableLiveData<UiModel>()

    val model: LiveData<UiModel>
        get() {
            if (_model.value == null) refresh()
            return _model
        }

    sealed class UiModel {
        object Loading : UiModel()
        class Content(val movies: List<Movie>) : UiModel()
    }

    private fun loadPopularMovies() {
        viewModelScope.launch {
            _model.value = UiModel.Loading
            _model.value =
                UiModel.Content(moviesRepository.getPopularMovies(
                    "2a78f529dbe1372f6db020930a705fa4"
                ).results)
        }
    }

    private fun refresh() {
        loadPopularMovies()
    }
}