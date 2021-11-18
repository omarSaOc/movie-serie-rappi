package com.oaso.movie_series_rappi.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.oaso.movie_series_rappi.model.server.Movie

class DetailMovieViewModel() : ViewModel() {

    private lateinit var movie : Movie

    class UiModel(val movie: Movie)

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            if (_model.value == null) {
                _model.value = UiModel(movie)
            }
            return _model
        }

    fun setMovie(movie : Movie){
        this.movie = movie
    }
}