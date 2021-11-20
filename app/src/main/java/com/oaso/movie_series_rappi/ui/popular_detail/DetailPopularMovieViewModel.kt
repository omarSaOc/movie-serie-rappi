package com.oaso.movie_series_rappi.ui.popular_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.oaso.movie_series_rappi.model.database.popular_movie.PopularMovie

class DetailPopularMovieViewModel :
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