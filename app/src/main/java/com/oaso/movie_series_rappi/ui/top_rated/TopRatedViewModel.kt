package com.oaso.movie_series_rappi.ui.top_rated

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oaso.movie_series_rappi.model.database.popular_movie.PopularMovie
import com.oaso.movie_series_rappi.model.database.rated_movie.RatedMovie
import com.oaso.movie_series_rappi.model.server.MoviesRepository
import com.oaso.movie_series_rappi.ui.common.Event
import com.oaso.movie_series_rappi.ui.popular.PopularViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopRatedViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository
) : ViewModel() {

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            if (_model.value == null) refresh()
            return _model
        }

    private val _navigation = MutableLiveData<Event<RatedMovie>>()
    val navigation: LiveData<Event<RatedMovie>> = _navigation

    sealed class UiModel {
        object Loading : UiModel()
        class Content(val ratedMovies: List<RatedMovie>) : UiModel()
    }

    private fun loadTopRatedMovies() {
        viewModelScope.launch {
            _model.value = UiModel.Loading
            _model.value =
                UiModel.Content(
                    moviesRepository.getTopRatedMovies()
                )
        }
    }

    private fun refresh() {
        loadTopRatedMovies()
    }

    fun onMovieClicked(ratedMovie: RatedMovie) {
        _navigation.value = Event(ratedMovie)
    }
}