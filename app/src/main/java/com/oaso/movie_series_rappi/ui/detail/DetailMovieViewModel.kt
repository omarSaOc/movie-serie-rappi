package com.oaso.movie_series_rappi.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.oaso.movie_series_rappi.model.database.popular_movie.PopularMovie
import com.oaso.movie_series_rappi.model.server.Movie
import com.oaso.movie_series_rappi.model.server.MoviesRepository
import javax.inject.Inject

class DetailMovieViewModel :
    ViewModel() {

    private lateinit var movie: PopularMovie

    class UiModel(val movie: PopularMovie)

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            if (_model.value == null) {
                _model.value = UiModel(movie)
            }
            return _model
        }

    fun setMovie(movie: PopularMovie) {
        this.movie = movie
    }
}