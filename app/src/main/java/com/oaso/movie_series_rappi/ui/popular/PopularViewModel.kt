package com.oaso.movie_series_rappi.ui.popular

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oaso.movie_series_rappi.model.database.popular_movie.PopularMovie
import com.oaso.movie_series_rappi.model.server.MoviesRepository
import com.oaso.movie_series_rappi.ui.common.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopularViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository
) : ViewModel() {

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            if (_model.value == null) refresh()
            return _model
        }

    private val _navigation = MutableLiveData<Event<PopularMovie>>()
    val navigation: LiveData<Event<PopularMovie>> = _navigation

    sealed class UiModel {
        object Loading : UiModel()
        class Content(val popularMovies: List<PopularMovie>) : UiModel()
        class LoadMoreMovies(val popularMovies: List<PopularMovie>) : UiModel()
    }

    private fun loadPopularMovies() {
        viewModelScope.launch {
            _model.value = UiModel.Loading
            _model.value =
                UiModel.Content(
                    moviesRepository.getPopularMovies()
                )
        }
    }

     fun loadMorePopularMovies(page : Int){
        viewModelScope.launch {
            _model.value = UiModel.Loading
            _model.value =
                UiModel.LoadMoreMovies(
                    moviesRepository.getMorePopularMovies(page)
                )
        }
    }

    private fun refresh() {
        loadPopularMovies()
    }

    fun onMovieClicked(popularMovie: PopularMovie) {
        _navigation.value = Event(popularMovie)
    }
}